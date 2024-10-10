package com.music.common.notifications.domain;

import com.music.common.comment.domain.Comment;
import com.music.common.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CommentNotifications extends Notifications {
    @JoinColumn(name = "COMMENT_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Comment comment;

    private CommentNotifications(User recipient, String message, Comment comment) {
        super(recipient, message);
        this.comment = comment;
    }

    public static CommentNotifications create(User recipient, String message, Comment comment) {
        require(nonNull(recipient));
        require(Strings.isNotBlank(message));
        require(nonNull(comment));

        return new CommentNotifications(recipient, message, comment);
    }
}
