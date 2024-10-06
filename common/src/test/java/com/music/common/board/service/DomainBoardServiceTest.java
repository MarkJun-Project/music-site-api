package com.music.common.board.service;

import com.music.common.attachment.domain.Attachment;
import com.music.common.attachment.domain.AttachmentRepository;
import com.music.common.board.domain.Board;
import com.music.common.support.BaseServiceTest;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.AttachmentFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.code.MusicCategory.EDM;
import static org.assertj.core.api.Assertions.assertThat;

class DomainBoardServiceTest extends BaseServiceTest {

    @Autowired
    private DomainBoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;


    private User user;
    private Attachment attachment;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.create());
        attachment = attachmentRepository.save(AttachmentFixture.create());
    }

    @Test
    void 게시글_생성_성공() {
        Board board = boardService.create(
                user.getId(),
                attachment.getId(),
                "title",
                "description",
                "songTitle",
                EDM
        );

        assertThat(board.getId()).isNotNull();
        assertThat(board.getUser().getId()).isEqualTo(user.getId());
        assertThat(board.getFile().getId()).isEqualTo(attachment.getId());
        assertThat(board.getTitle()).isEqualTo("title");
        assertThat(board.getDescription()).isEqualTo("description");
        assertThat(board.getSongTitle()).isEqualTo("songTitle");
        assertThat(board.getMusicCategory()).isEqualTo(EDM);
    }
}
