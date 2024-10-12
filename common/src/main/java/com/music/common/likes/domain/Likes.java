package com.music.common.likes.domain;

import com.music.common.board.domain.Board;
import com.music.common.support.BaseEntity;
import com.music.common.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes extends BaseEntity {
    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @JoinColumn(name = "BOARD_ID", nullable = false)
    @ManyToOne
    private Board board;

    private Likes(User user, Board board) {
        board.addLikes(this);

        this.user = user;
        this.board = board;
    }

    public static Likes create(User user, Board board) {
        require(nonNull(user));
        require(nonNull(board));

        return new Likes(user, board);
    }

    public boolean isUser(User user) {
        return this.user == user;
    }
}
