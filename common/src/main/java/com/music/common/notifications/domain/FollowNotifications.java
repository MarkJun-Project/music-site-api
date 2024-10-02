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
public class FollowNotifications extends Notifications {
    @JoinColumn(name = "FOLLOW_ID", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Follow follow;

    private FollowNotifications(User recipient, String message, Follow follow) {
        super(recipient, message);
        this.follow = follow;
    }

    public static FollowNotifications create(User recipient, String message, Follow follow) {
        require(nonNull(recipient));
        require(Strings.isNotBlank(message));
        require(nonNull(follow));

        return new FollowNotifications(recipient, message, follow);
    }
}
