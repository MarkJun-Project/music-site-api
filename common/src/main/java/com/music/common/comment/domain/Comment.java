package com.music.common.comment.domain;

import com.music.common.board.domain.Board;
import com.music.common.support.BaseEntity;
import com.music.common.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

import static com.music.common.comment.domain.CommentStatus.CAN_DELETE_STATUS;
import static com.music.common.support.Preconditions.*;
import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends BaseEntity {
    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "BOARD_ID", nullable = false)
    @ManyToOne
    private Board board;

    @JoinColumn(name = "PARENT_ID")
    @ManyToOne
    private Comment parent;

    @Column(name = "COMMENT", nullable = false)
    private String comment;

    @Setter
    @Column(name = "COMMENT_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommentStatus status = CommentStatus.CREATED;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    public boolean isUser(User user) {
        return this.user == user;
    }

    // 댓글 생성자
    private Comment(User user, Board board, String comment) {
        this.user = user;
        this.board = board;
        this.comment = comment;
    }

    // 대댓글 생성자
    private Comment(User user, Board board, String comment, Comment parent) {
        parent.addChild(this);

        this.user = user;
        this.board = board;
        this.comment = comment;
        this.parent = parent;
    }

    public static Comment createComment(User user, Board board, String comment) {
        require(nonNull(user));
        require(nonNull(board));
        require(Strings.isNotBlank(comment));

        return new Comment(user, board, comment);
    }

    public static Comment createReply(User user, Board board, String comment, Comment parent) {
        require(nonNull(user));
        require(nonNull(board));
        require(Strings.isNotBlank(comment));
        require(nonNull(parent));

        return new Comment(user, board, comment, parent);
    }

    private void addChild(Comment child) {
        children.add(child);
    }

    public void update(String comment) {
        require(Strings.isNotBlank(comment));

        this.comment = comment;
    }

    public void delete() {
        check(CAN_DELETE_STATUS.contains(status));

        this.status = CommentStatus.DELETED;
    }
}
