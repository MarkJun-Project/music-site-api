package com.music.common.comment.service;

import com.music.common.attachment.domain.Attachment;
import com.music.common.attachment.domain.AttachmentRepository;
import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.board.service.BoardService;
import com.music.common.code.MusicCategory;
import com.music.common.comment.domain.Comment;
import com.music.common.comment.domain.CommentRepository;
import com.music.common.comment.domain.CommentStatus;
import com.music.common.support.BaseServiceTest;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.AttachmentFixture;
import fixtures.BoardFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.code.MusicCategory.EDM;
import static com.music.common.support.ExceptionTest.assertThatActorValidateCustomException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;


public class DomainCommentServiceTest extends BaseServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;
    private User user;
    private Board board;
    private Attachment attachment;


    @BeforeEach
    void setUp() {
        user = userRepository.save(UserFixture.create());

        attachment = attachmentRepository.save(AttachmentFixture.create());

        board = boardRepository.save(
                Board.create(
                        user,
                        attachment,
                        "title",
                        "description",
                        "songTitle",
                        EDM
        ));
    }

    @Test
    void 게시글_댓글_생성_성공() {
        Comment comment = commentService.create(board.getId(), user.getId(), "comment");

        assertThat(board.getId()).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getComment()).isEqualTo("comment");
    }

    @Test
    void 게시글_댓글_수정_성공() {
        Comment comment = commentService.create(board.getId(), user.getId(), "comment");
        commentService.update(comment.getId(), user.getId(), "updateComment");

        assertThat(comment.getComment()).isEqualTo("updateComment");
    }

    @Test
    void 게시글_댓글_수정_실패__작성자가_아님() {
        Comment comment = commentService.create(board.getId(), user.getId(), "comment");
        User fakeUser = userRepository.save(UserFixture.create());

        assertThatActorValidateCustomException().isThrownBy(() -> commentService.update(comment.getId(), fakeUser.getId(), "updatedComment"));
    }

    @Test
    void 게시글_댓글_삭제_성공(){
        Comment comment = commentService.create(board.getId(), user.getId(), "comment");

        commentService.delete(comment.getId(), user.getId());

        Comment findComment = commentRepository.findAll().get(0);

        assertThat(findComment.getStatus()).isEqualTo(CommentStatus.DELETED);
    }

    @Test
    void 게시글_댓글_삭제_실패__작성자가_아님(){
        Comment comment = commentService.create(board.getId(), user.getId(), "comment");
        User fakeUser = userRepository.save(UserFixture.create());

        assertThatActorValidateCustomException().isThrownBy(() -> commentService.update(comment.getId(), fakeUser.getId(), "updatedComment"));
    }


}
