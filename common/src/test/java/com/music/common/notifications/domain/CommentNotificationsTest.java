package com.music.common.notifications.domain;


import com.music.common.comment.domain.Comment;
import com.music.common.user.domain.User;
import fixtures.CommentFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class CommentNotificationsTest {
    private User recipient;
    private Comment comment;

    @BeforeEach
    void setUp() {
        this.recipient = UserFixture.create();
        this.comment = CommentFixture.createComment();
    }

    @Test
    void 댓글_알림_생성_성공() {
        // when
        CommentNotifications commentNotifications = CommentNotifications.create(recipient, "새로운 댓글이 등록되었습니다.", comment);

        // then
        assertThat(commentNotifications).isNotNull();
        assertThat(commentNotifications.getRecipient()).isEqualTo(recipient);
        assertThat(commentNotifications.getComment()).isEqualTo(comment);
        assertThat(commentNotifications.getStatus()).isEqualTo(NotificationsStatus.CREATED);
        assertThat(commentNotifications.getMessage()).isEqualTo("새로운 댓글이 등록되었습니다.");
    }

    @Test
    void 댓글_알림_생성_실패_유저_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CommentNotifications.create(null, "새로운 댓글이 등록되었습니다.", comment));
    }

    @Test
    void 댓글_알림_생성_실패_댓글_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CommentNotifications.create(recipient, "새로운 댓글이 등록되었습니다.", null));
    }

    @ParameterizedTest
    @NullSource
    void 댓글_알림_생성_실패_유저_null_혹은_빈값(User invalidUser) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CommentNotifications.create(invalidUser, "새로운 댓글이 등록되었습니다", comment));
    }

    @ParameterizedTest
    @NullSource
    void 댓글_알림_생성_실패_댓글_null_혹은_빈값(Comment invalidComment) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CommentNotifications.create(recipient, "새로운 댓글이 등록되었습니다", invalidComment));
    }

    @Test
    void 댓글_알림_생성_실패_메시지_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CommentNotifications.create(recipient, null, comment));
    }

    @Test
    void 댓글_알림_생성_실패_메시지_빈값() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CommentNotifications.create(recipient, "", comment));
    }

    @Test
    void 댓글_알림_생성_실패_메시지_공백() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CommentNotifications.create(recipient, "   ", comment));
    }
}