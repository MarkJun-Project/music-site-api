package com.music.common.notifications.domain;

import com.music.common.likes.domain.Likes;
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
public class LikesNotifications extends Notifications {
    @JoinColumn(name = "LIKES_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Likes likes;

    private LikesNotifications(User recipient, String message, Likes likes) {
        super(recipient, message);
        this.likes = likes;
    }

    public static LikesNotifications create(User recipient, String message, Likes likes) {
        require(nonNull(recipient));
        require(Strings.isNotBlank(message));
        require(nonNull(likes));

        return new LikesNotifications(recipient, message, likes);
    }
}
