package com.music.common.musicscore.service;

import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.code.MusicCategory;
import com.music.common.musicscore.domain.MusicScore;
import com.music.common.musicscore.domain.Score;
import com.music.common.support.BaseServiceTest;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.AttachmentFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class DomainMusicScoreServiceTest extends BaseServiceTest {

    @Autowired
    private DomainMusicScoreService musicScoreService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    private User user;
    private Board board;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.create());
        board = boardRepository.save(
                Board.create(
                        user,
                        AttachmentFixture.create(),
                        "title",
                        "description",
                        "songTitle",
                        MusicCategory.ROCK
                )
        );
    }

    @Test
    void 음악_점수_생성_성공() {
        // given
        Score three = Score.THREE;

        // when
        MusicScore musicScore = musicScoreService.create(user.getId(), board.getId(), three);

        // then
        assertThat(musicScore.getId()).isNotNull();
        assertThat(musicScore.getUser()).isEqualTo(user);
        assertThat(musicScore.getBoard()).isEqualTo(board);
        assertThat(musicScore.getScore()).isEqualTo(three);
    }

    @Test
    void 음악_점수_생성_실패_이미_평가한_게시글() {
        // given
        Score three = Score.THREE;
        musicScoreService.create(user.getId(), board.getId(), three);

        // when & then
        assertThatIllegalStateException()
                .isThrownBy(() -> musicScoreService.create(user.getId(), board.getId(), three))
                .withMessage("이미 평가한 게시글입니다.");
    }
}