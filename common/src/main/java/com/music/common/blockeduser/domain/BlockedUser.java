package com.music.common.blockeduser.domain;

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
public class BlockedUser extends BaseEntity {
    @JoinColumn(name = "BLOCKER_ID")
    @ManyToOne
    private User blocker; // 차단한 사용자

    @JoinColumn(name = "BLOCKED_ID")
    @ManyToOne
    private User blocked; // 차단된 사용자

    private BlockedUserStatus blockedUserStatus = BlockedUserStatus.CREATED;

    private BlockedUser(User blocker, User blocked) {
        blocker.addBlocker(this);
        blocked.addBlocked(this);

        this.blocker = blocker;
        this.blocked = blocked;
    }

    public static BlockedUser create(User blocker, User blocked) {
        require(nonNull(blocker));
        require(nonNull(blocked));

        return new BlockedUser(blocker, blocked);
    }
}
