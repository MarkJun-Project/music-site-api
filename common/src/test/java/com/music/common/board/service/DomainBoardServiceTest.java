package com.music.common.board.service;

import com.music.common.attachment.domain.Attachment;
import com.music.common.attachment.domain.AttachmentRepository;
import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.support.BaseServiceTest;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.AttachmentFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.code.MusicCategory.EDM;
import static com.music.common.support.ExceptionTest.assertThatActorValidateCustomException;
import static org.assertj.core.api.Assertions.assertThat;

class DomainBoardServiceTest extends BaseServiceTest {

    @Autowired
    private DomainBoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private BoardRepository boardRepository;

    private User user;
    private Attachment attachment;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.create());
        attachment = attachmentRepository.save(AttachmentFixture.create());
    }

    @Test
    void 게시글_생성_성공() {
        // given

        // when
        Board board = boardService.create(
                user.getId(),
                attachment.getId(),
                "title",
                "description",
                "songTitle",
                EDM
        );

        // then
        assertThat(board.getId()).isNotNull();
        assertThat(board.getUser().getId()).isEqualTo(user.getId());
        assertThat(board.getFile().getId()).isEqualTo(attachment.getId());
        assertThat(board.getTitle()).isEqualTo("title");
        assertThat(board.getDescription()).isEqualTo("description");
        assertThat(board.getSongTitle()).isEqualTo("songTitle");
        assertThat(board.getMusicCategory()).isEqualTo(EDM);
    }

    @Test
    void 게시글_수정_실패_작성한_유저가_아님() {
        // given
        User fakeUser = userRepository.save(UserFixture.create());
        Board board = boardService.create(
                user.getId(),
                attachment.getId(),
                "title",
                "description",
                "songTitle",
                EDM
        );

        // when & then
        assertThatActorValidateCustomException()
                .isThrownBy(() -> boardService.update(
                        board.getId(),
                        fakeUser.getId(),
                        "changedTitle",
                        "changedDescription")
                );
    }

    @Test
    void 게시글_수정_성공() {
        // given
        Board board = boardService.create(
                user.getId(),
                attachment.getId(),
                "title",
                "description",
                "songTitle",
                EDM
        );

        // when
        boardService.update(board.getId(), user.getId(), "changedTitle", "changedDescription");
        Board updatedBoard = boardRepository.findById(board.getId()).orElseThrow();

        // then
        assertThat(updatedBoard.getTitle()).isEqualTo("changedTitle");
        assertThat(updatedBoard.getDescription()).isEqualTo("changedDescription");
    }
}
