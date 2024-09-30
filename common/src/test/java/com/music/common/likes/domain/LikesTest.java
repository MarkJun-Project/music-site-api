package com.music.common.likes.domain;

import com.music.common.board.domain.Board;
import com.music.common.user.domain.User;
import fixtures.BoardFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.assertj.core.api.Assertions.*;

class LikesTest {
    private User user;
    private Board board;

    @BeforeEach
    void setUp() {
        this.user = UserFixture.create();
        this.board = BoardFixture.create();
    }

    @Test
    void 좋아요_생성_성공() {
        // given

        // when
        Likes likes = Likes.create(user, board);

        // then
        assertThat(likes).isNotNull();
        assertThat(likes.getUser()).isEqualTo(user);
        assertThat(likes.getBoard()).isEqualTo(board);
    }

    @Test
    void 좋아요_생성_실패_유저_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Likes.create(null, board));
    }

    @Test
    void 좋아요_생성_실패_게시글_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Likes.create(user, null));
    }

    @ParameterizedTest
    @NullSource
    void 좋아요_생성_실패_유저_null_혹은_빈값(User invalidUser) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Likes.create(invalidUser, board));
    }

    @ParameterizedTest
    @NullSource
    void 좋아요_생성_실패_게시글_null_혹은_빈값(Board invalidBoard) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Likes.create(user, invalidBoard));
    }
}
