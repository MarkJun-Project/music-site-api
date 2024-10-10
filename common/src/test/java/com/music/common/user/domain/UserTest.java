package com.music.common.user.domain;

import com.music.common.code.SocialType;
import fixtures.UserFixture;
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

        // when
        User user = User.create(socialId, socialType, email);

        // then
        assertThat(user.getSocialId()).isEqualTo(socialId);
        assertThat(user.getSocialType()).isEqualTo(socialType);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 유저_생성_실패_소셜ID_값_null_혹은_빈값(String invalidSocialId) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create(invalidSocialId, SocialType.KAKAO, "email"));
    }

    @Test
    void 유저_생성_실패_소셜타입_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create("socialId", null, "email"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 유저_생성_실패_이메일_값_null_혹은_빈값(String invalidEmail) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> User.create("socialId", SocialType.KAKAO, invalidEmail));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 닉네임_변경_실패_null_혹은_빈값(String invalidNickname) {
        // given
        User user = UserFixture.create();

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> user.updateNickname(invalidNickname));
    }

    @Test
    void 닉네임_변경_성공() {
        // given
        String nickname = "nickname";
        User user = UserFixture.create();

        // when
        user.updateNickname(nickname);

        // then
        assertThat(user.getNickname()).isEqualTo(nickname);
    }
}
