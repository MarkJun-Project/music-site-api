package com.music.common.user.domain;

import com.music.common.code.SocialType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class UserTest {

    @Test
    void 유저_생성_성공() {
        // given
        String socialId = "socialId";
        SocialType socialType = SocialType.KAKAO;
        String email = "email";
        String nickname = "nickname";

        // when
        User user = User.create(socialId, socialType, email, nickname);

        // then
        assertThat(user.getSocialId()).isEqualTo(socialId);
        assertThat(user.getSocialType()).isEqualTo(socialType);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getNickname()).isEqualTo(nickname);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 유저_생성_실패_소셜ID_값_null_혹은_빈값(String invalidSocialId) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create(invalidSocialId, SocialType.KAKAO, "email", "nickname"));
    }

    @Test
    void 유저_생성_실패_소셜타입_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create("socialId", null, "email", "nickname"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 유저_생성_실패_이메일_값_null_혹은_빈값(String invalidEmail) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create("socialId", SocialType.KAKAO, invalidEmail, "nickname"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 유저_생성_실패_닉네임_값_null_혹은_빈값(String invalidNickname) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create("socialId", SocialType.KAKAO, "email", invalidNickname));
    }
}
