package com.music.common.notifications.domain;

import com.music.common.board.domain.Board;
import com.music.common.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@DiscriminatorValue("B")
public class BoardNotifications extends Notifications {
    @JoinColumn(name = "BOARD_ID", nullable = false)
    @ManyToOne
    private Board board;

    private BoardNotifications(User recipient, NotificationsType notificationsType, String message, Board board) {
        super(recipient, notificationsType, message);
        this.board = board;
    }

    public static BoardNotifications create(User recipient, NotificationsType notificationsType, String message, Board board) {
        require(nonNull(recipient));
        require(nonNull(notificationsType));
        require(Strings.isNotBlank(message));
        require(nonNull(board));

        return new BoardNotifications(recipient, notificationsType, message, board);
    }
}
