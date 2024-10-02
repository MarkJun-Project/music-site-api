package com.music.common.notifications.domain;


import com.music.common.board.domain.Board;
import com.music.common.user.domain.User;
import fixtures.BoardFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BoardNotificationsTest {
    private User recipient;
    private Board board;

    @BeforeEach
    void setUp() {
        this.recipient = UserFixture.create();
        this.board = BoardFixture.create();
    }

    @Test
    void 게시글_알림_생성_성공() {
        // when
        BoardNotifications boardNotification = BoardNotifications.create(recipient, "게시글이 등록되었습니다", board);

        // then
        assertThat(boardNotification).isNotNull();
        assertThat(boardNotification.getRecipient()).isEqualTo(recipient);
        assertThat(boardNotification.getBoard()).isEqualTo(board);
        assertThat(boardNotification.getMessage()).isEqualTo("게시글이 등록되었습니다");
    }

    @Test
    void 게시글_알림_생성_실패_유저_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BoardNotifications.create(null, "게시글이 등록되었습니다", board));
    }

    @Test
    void 게시글_알림_생성_실패_게시글_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BoardNotifications.create(recipient, "게시글이 등록되었습니다", null));
    }

    @ParameterizedTest
    @NullSource
    void 게시글_알림_생성_실패_유저_null_혹은_빈값(User invalidUser) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BoardNotifications.create(invalidUser, "게시글이 등록되었습니다", board));
    }

    @ParameterizedTest
    @NullSource
    void 게시글_알림_생성_실패_게시글_null_혹은_빈값(Board invalidBoard) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BoardNotifications.create(recipient, "게시글이 등록되었습니다", invalidBoard));
    }

    @Test
    void 게시글_알림_생성_실패_메시지_null() {
        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BoardNotifications.create(recipient, null, board));
    }

    @Test
    void 게시글_알림_생성_실패_메시지_빈값() {
        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BoardNotifications.create(recipient, "", board));
    }

    @Test
    void 게시글_알림_생성_실패_메시지_공백() {
        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BoardNotifications.create(recipient, "   ", board));
    }
}