package com.music.common.user.domain;

import com.music.common.code.SocialType;
import com.music.common.support.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.math.BigDecimal;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@Entity
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
}
