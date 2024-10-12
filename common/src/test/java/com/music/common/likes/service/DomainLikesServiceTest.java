package com.music.common.likes.service;


import com.music.common.attachment.domain.Attachment;
import com.music.common.attachment.domain.AttachmentRepository;
import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.likes.domain.Likes;
import com.music.common.support.BaseServiceTest;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.AttachmentFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.code.MusicCategory.EDM;
import static org.assertj.core.api.Assertions.*;

class DomainLikesServiceTest extends BaseServiceTest {

    @Autowired
    private DomainLikesService domainLikesService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private BoardRepository boardRepository;

    private Board board;
    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.create());
        Attachment attachment = attachmentRepository.save(AttachmentFixture.create());
        board = boardRepository.save(
                Board.create(
                        user,
                        attachment,
                        "title",
                        "description",
                        "songTitle",
                        EDM
                )
        );
    }

    @Test
    void 좋아요_생성_성공() {
        // given

        // when
        Likes likes = domainLikesService.create(user.getId(), board.getId());

        // then
        assertThat(likes.getId()).isNotNull();
        assertThat(likes.getUser().getId()).isEqualTo(user.getId());
        assertThat(likes.getBoard().getId()).isEqualTo(board.getId());
    }
}