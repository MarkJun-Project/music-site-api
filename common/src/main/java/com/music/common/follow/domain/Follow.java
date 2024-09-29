package com.music.common.follow.domain;

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
public class Follow extends BaseEntity {
    @JoinColumn(name = "FOLLOWER_ID", nullable = false)
    @ManyToOne
    private User follower; // 팔로우한 유저

    @JoinColumn(name = "FOLLOWEE_ID", nullable = false)
    @ManyToOne
    private User followee; // 팔로우된 유저

    private Follow(User follower, User followee) {
        follower.addFollowing(this);
        followee.addFollowers(this);

        this.follower = follower;
        this.followee = followee;
    }

    public static Follow create(User follower, User followee) {
        require(nonNull(follower));
        require(nonNull(followee));

        return new Follow(follower, followee);
    }
}
