package com.music.common.notifications.domain;

import com.music.common.likes.domain.Likes;
import com.music.common.user.domain.User;
import fixtures.LikesFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class LikesNotificationsTest {
    private User recipient;
    private Likes likes;

    @BeforeEach
    void setUp() {
        this.recipient = UserFixture.create();
        this.likes = LikesFixture.create();
    }

    @Test
    void 좋아요_알림_생성_성공() {
        // when
        LikesNotifications likesNotification = LikesNotifications.create(recipient, "좋아요가 등록되었습니다", likes);

        // then
        assertThat(likesNotification).isNotNull();
        assertThat(likesNotification.getRecipient()).isEqualTo(recipient);
        assertThat(likesNotification.getMessage()).isEqualTo("좋아요가 등록되었습니다");
        assertThat(likesNotification.getStatus()).isEqualTo(NotificationsStatus.CREATED);
        assertThat(likesNotification.getLikes()).isEqualTo(likes);
    }

    @Test
    void 좋아요_알림_생성_실패_유저_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LikesNotifications.create(null, "좋아요가 등록되었습니다", likes));
    }

    @Test
    void 좋아요_알림_생성_실패_좋아요_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LikesNotifications.create(recipient, "좋아요가 등록되었습니다", null));
    }

    @ParameterizedTest
    @NullSource
    void 좋아요_알림_생성_실패_유저_null_혹은_빈값(User invalidUser) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LikesNotifications.create(invalidUser, "좋아요가 등록되었습니다", likes));
    }

    @ParameterizedTest
    @NullSource
    void 좋아요_알림_생성_실패_좋아요_null_혹은_빈값(Likes invalidLikes) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LikesNotifications.create(recipient, "좋아요가 등록되었습니다", invalidLikes));
    }

    @Test
    void 좋아요_알림_생성_실패_메시지_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LikesNotifications.create(recipient, null, likes));
    }

    @Test
    void 좋아요_알림_생성_실패_메시지_빈값() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LikesNotifications.create(recipient, "", likes));
    }

    @Test
    void 좋아요_알림_생성_실패_메시지_공백() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> LikesNotifications.create(recipient, "   ", likes));
    }
}