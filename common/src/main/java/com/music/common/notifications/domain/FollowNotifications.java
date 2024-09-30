package com.music.common.notifications.domain;

import com.music.common.follow.domain.Follow;
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
@DiscriminatorValue("F")
public class FollowNotifications extends Notifications {
    @JoinColumn(name = "FOLLOW_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Follow follow;

    private FollowNotifications(User recipient, NotificationsType notificationsType, String message, Follow follow) {
        super(recipient, notificationsType, message);
        this.follow = follow;
    }

    public static FollowNotifications create(User recipient, NotificationsType notificationsType, String message, Follow follow) {
        require(nonNull(recipient));
        require(nonNull(notificationsType));
        require(Strings.isNotBlank(message));
        require(nonNull(follow));

        return new FollowNotifications(recipient, notificationsType, message, follow);
    }
}
