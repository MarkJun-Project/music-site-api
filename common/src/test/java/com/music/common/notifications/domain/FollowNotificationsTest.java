package com.music.common.notifications.domain;


import com.music.common.follow.domain.Follow;
import com.music.common.user.domain.User;
import fixtures.FollowFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class FollowNotificationsTest {
    private User recipient;
    private Follow follow;

    @BeforeEach
    void setUp() {
        this.recipient = UserFixture.create();
        this.follow = FollowFixture.create();
    }

    @Test
    void 팔로우_알림_생성_성공() {
        // when
        FollowNotifications followNotification = FollowNotifications.create(recipient, "새로운 팔로우가 등록되었습니다", follow);

        // then
        assertThat(followNotification).isNotNull();
        assertThat(followNotification.getRecipient()).isEqualTo(recipient);
        assertThat(followNotification.getFollow()).isEqualTo(follow);
        assertThat(followNotification.getMessage()).isEqualTo("새로운 팔로우가 등록되었습니다");
    }

    @Test
    void 팔로우_알림_생성_실패_유저_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FollowNotifications.create(null, "새로운 팔로우가 등록되었습니다", follow));
    }

    @Test
    void 팔로우_알림_생성_실패_팔로우_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FollowNotifications.create(recipient, "새로운 팔로우가 등록되었습니다", null));
    }

    @ParameterizedTest
    @NullSource
    void 팔로우_알림_생성_실패_유저_null_혹은_빈값(User invalidUser) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FollowNotifications.create(invalidUser, "새로운 팔로우가 등록되었습니다", follow));
    }

    @ParameterizedTest
    @NullSource
    void 팔로우_알림_생성_실패_팔로우_null_혹은_빈값(Follow invalidFollow) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FollowNotifications.create(recipient, "새로운 팔로우가 등록되었습니다", invalidFollow));
    }

    @Test
    void 팔로우_알림_생성_실패_메시지_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FollowNotifications.create(recipient, null, follow));
    }

    @Test
    void 팔로우_알림_생성_실패_메시지_빈값() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FollowNotifications.create(recipient, "", follow));
    }

    @Test
    void 팔로우_알림_생성_실패_메시지_공백() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> FollowNotifications.create(recipient, "   ", follow));
    }
}