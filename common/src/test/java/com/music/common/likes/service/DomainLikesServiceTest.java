package com.music.common.likes.service;


import com.music.common.attachment.domain.Attachment;
import com.music.common.attachment.domain.AttachmentRepository;
import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.likes.domain.Likes;
import com.music.common.likes.domain.LikesRepository;
import com.music.common.support.BaseServiceTest;
import com.music.common.support.CustomException;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.AttachmentFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.code.MusicCategory.EDM;
import static com.music.common.support.ErrorCode.*;
import static com.music.common.support.ExceptionTest.assertThatActorValidateCustomException;
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

    @Autowired
    private LikesRepository likesRepository;

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

    @Test
    void 좋아요_생성_실패_이미_존재하는_좋아요() {
        // given
        domainLikesService.create(user.getId(), board.getId());

        // when & then
        assertThatThrownBy(() -> domainLikesService.create(user.getId(), board.getId()))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ALREADY_LIKED);
    }

    @Test
    void 좋아요_삭제_성공() {
        // given
        domainLikesService.create(user.getId(), board.getId());
        Likes likes = likesRepository.findAll().get(0);

        // when
        domainLikesService.delete(likes.getId(), user.getId());

        // then
        assertThat(likesRepository.findById(likes.getId()).isEmpty()).isTrue();
    }

    @Test
    void 좋아요_삭제_실패_좋아요_누른_유저가_아님() {
        // given
        domainLikesService.create(user.getId(), board.getId());
        Likes likes = likesRepository.findAll().get(0);
        User otherUser = userRepository.save(UserFixture.create());

        // when & then
        assertThatActorValidateCustomException()
                .isThrownBy(() -> domainLikesService.delete(likes.getId(), otherUser.getId()));
    }
}