package com.music.common.musicscore.domain;


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

class MusicScoreTest {
    private User user;
    private Board board;

    @BeforeEach
    void setUp() {
        this.user = UserFixture.create();
        this.board = BoardFixture.create();
    }

    @Test
    void 음악점수_생성_성공() {
        // given
        Score score = Score.FIVE;

        // when
        MusicScore musicScore = MusicScore.create(user, board, score);

        // then
        assertThat(musicScore).isNotNull();
        assertThat(musicScore.getUser()).isEqualTo(user);
        assertThat(musicScore.getBoard()).isEqualTo(board);
        assertThat(musicScore.getScore()).isEqualTo(score);
    }

    @Test
    void 음악점수_생성_실패_유저_null() {
        // given
        Score score = Score.FIVE;

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> MusicScore.create(null, board, score));
    }

    @Test
    void 음악점수_생성_실패_게시글_null() {
        // given
        Score score = Score.FIVE;

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> MusicScore.create(user, null, score));
    }

    @Test
    void 음악점수_생성_실패_점수_null() {
        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> MusicScore.create(user, board, null));
    }

    @ParameterizedTest
    @NullSource
    void 음악점수_생성_실패_유저_null_혹은_빈값(User invalidUser) {
        Score score = Score.FIVE;
        assertThatIllegalArgumentException()
                .isThrownBy(() -> MusicScore.create(invalidUser, board, score));
    }

    @ParameterizedTest
    @NullSource
    void 음악점수_생성_실패_게시글_null_혹은_빈값(Board invalidBoard) {
        Score score = Score.FIVE;
        assertThatIllegalArgumentException()
                .isThrownBy(() -> MusicScore.create(user, invalidBoard, score));
    }

    @ParameterizedTest
    @NullSource
    void 음악점수_생성_실패_점수_null_혹은_빈값(Score invalidScore) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> MusicScore.create(user, board, invalidScore));
    }
}
