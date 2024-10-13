package com.music.common.board.domain;

import com.music.common.attachment.domain.Attachment;
import com.music.common.code.MusicCategory;
import com.music.common.likes.domain.Likes;
import com.music.common.support.BaseEntity;
import com.music.common.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "ATTACHMENT_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Attachment file;

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "SONG_TITLE", length = 100, nullable = false)
    private String songTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 10, nullable = false)
    private BoardStatus status = BoardStatus.CREATED;

    @Enumerated(EnumType.STRING)
    @Column(name = "MUSIC_CATEGORY", length = 100, nullable = false)
    private MusicCategory musicCategory;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes = new ArrayList<>();

    private Board(User user, Attachment file, String title, String description, String songTitle, MusicCategory musicCategory) {
        this.user = user;
        this.file = file;
        this.title = title;
        this.description = description;
        this.songTitle = songTitle;
        this.musicCategory = musicCategory;
    }

    public static Board create(User user, Attachment file, String title, String description, String songTitle, MusicCategory musicCategory){
        require(nonNull(user));
        require(nonNull(file));
        require(Strings.isNotBlank(title));
        require(Strings.isNotBlank(description));
        require(Strings.isNotBlank(songTitle));
        require(nonNull(musicCategory));

        return new Board(user, file, title, description, songTitle, musicCategory);
    }

    public void addLikes(Likes likes) {
        this.likes.add(likes);
    }

    public boolean isUser(User user) {
        return this.user == user;
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
