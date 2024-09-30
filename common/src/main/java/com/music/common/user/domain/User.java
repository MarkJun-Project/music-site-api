package com.music.common.user.domain;

import com.music.common.attachment.domain.Attachment;
import com.music.common.blockeduser.domain.BlockedUser;
import com.music.common.code.SocialType;
import com.music.common.follow.domain.Follow;
import com.music.common.support.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@Entity
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(name = "SOCIAL_ID", length = 200, nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(name = "SOCIAL_TYPE", length = 200, nullable = false)
    private SocialType socialType;

    @Column(name = "EMAIL", length = 100, nullable = false)
    private String email;

    @Column(name = "NICKNAME", length = 16)
    private String nickname;

    @Column(name = "TIER_SCORE", precision = 19, scale = 2)
    private BigDecimal tierScore = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", length = 10, nullable = false)
    private UserStatus status = UserStatus.CREATED;

    @JoinColumn(name = "PROFILE_IMAGE_ID")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Attachment profileImage;

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "blocker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlockedUser> blockers = new ArrayList<>();

    @OneToMany(mappedBy = "blocked", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BlockedUser> blockeds = new ArrayList<>();

    private User(String socialId, SocialType socialType, String email, String nickname) {
        this.socialId = socialId;
        this.socialType = socialType;
        this.email = email;
        this.nickname = nickname;
    }

    public static User create(String socialId, SocialType socialType, String email, String nickname) {
        require(Strings.isNotBlank(socialId));
        require(nonNull(socialType));
        require(Strings.isNotBlank(email));
        require(Strings.isNotBlank(nickname));

        return new User(socialId, socialType, email, nickname);
    }

    public void addFollowing(Follow follower) {
        this.following.add(follower);
    }

    public void addFollowers(Follow followee) {
        this.followers.add(followee);
    }

    public void addBlocker(BlockedUser blocker) {
        this.blockers.add(blocker);
    }

    public void addBlocked(BlockedUser blocked) {
        this.blockeds.add(blocked);
    }
}
