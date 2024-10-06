package com.music.common.musicscore.domain;

import com.music.common.board.domain.Board;
import com.music.common.support.BaseEntity;
import com.music.common.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MusicScore extends BaseEntity {
    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "BOARD_ID", nullable = false)
    @ManyToOne
    private Board board;

    @Column(name = "SCORE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Score score;

    private MusicScore(User user, Board board, Score score) {
        this.user = user;
        this.board = board;
        this.score = score;
    }

    public static MusicScore create(User user, Board board, Score score) {
        require(nonNull(user));
        require(nonNull(board));
        require(nonNull(score));

        return new MusicScore(user, board, score);
    }
}
