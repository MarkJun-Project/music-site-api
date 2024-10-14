package com.music.common.comment.domain;


import com.music.common.board.domain.Board;
import com.music.common.user.domain.User;
import fixtures.BoardFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.music.common.comment.domain.CommentStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.EnumSource.Mode.*;

class CommentTest {
    private User user;
    private Board board;
    private Comment comment;

    @BeforeEach
    void setUp() {
        this.user = UserFixture.create();
        this.board = BoardFixture.create();
    }

    @Test
    void 댓글_생성_성공() {
        // Given
        String content = "This is a test comment.";

        // When
        Comment comment = Comment.createComment(user, board, content);

        // Then
        assertThat(comment).isNotNull();
        assertThat(comment.getUser()).isEqualTo(user);
        assertThat(comment.getBoard()).isEqualTo(board);
        assertThat(comment.getComment()).isEqualTo(content);
        assertThat(comment.getStatus()).isEqualTo(CREATED);
    }

    @Test
    void 대댓글_생성_성공() {
        // Given
        String parentContent = "This is a parent comment.";
        String replyContent = "This is a reply comment.";
        Comment parentComment = Comment.createComment(user, board, parentContent);

        // When
        Comment replyComment = Comment.createReply(user, board, replyContent, parentComment);

        // Then
        assertThat(replyComment).isNotNull();
        assertThat(replyComment.getParent()).isEqualTo(parentComment);
        assertThat(replyComment.getUser()).isEqualTo(user);
        assertThat(replyComment.getBoard()).isEqualTo(board);
        assertThat(replyComment.getComment()).isEqualTo(replyContent);
        assertThat(parentComment.getChildren()).contains(replyComment); // 부모 댓글에 대댓글이 포함되었는지 확인
    }

    @Test
    void 댓글_생성_실패_유저_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Comment.createComment(null, board, "Test comment"));
    }

    @Test
    void 댓글_생성_실패_게시글_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Comment.createComment(user, null, "Test comment"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 댓글_생성_실패_내용_null_혹은_빈값(String invalidContent) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Comment.createComment(user, board, invalidContent));
    }

    @Test
    void 대댓글_생성_실패_유저_null() {
        Comment parentComment = Comment.createComment(user, board, "Parent comment");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Comment.createReply(null, board, "Reply comment", parentComment));
    }

    @Test
    void 대댓글_생성_실패_게시글_null() {
        Comment parentComment = Comment.createComment(user, board, "Parent comment");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Comment.createReply(user, null, "Reply comment", parentComment));
    }

    @Test
    void 대댓글_생성_실패_부모댓글_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Comment.createReply(user, board, "Reply comment", null));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 대댓글_생성_실패_내용_null_혹은_빈값(String invalidContent) {
        Comment parentComment = Comment.createComment(user, board, "Parent comment");
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Comment.createReply(user, board, invalidContent, parentComment));
    }

    @Test
    void 댓글_수정_성공() {
        // Given
        String content = "This is a test comment.";
        Comment comment = Comment.createComment(user, board, content);

        // When
        String updateComment = "updateComment";
        comment.update(updateComment);

        assertThat(comment.getComment()).isEqualTo("updateComment");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 댓글_수정_실패_내용_null_혹은_빈값(String nullContent) {
        String content = "This is a test comment.";
        Comment comment = Comment.createComment(user, board, content);

        assertThatIllegalArgumentException().isThrownBy(() -> comment.update(nullContent));
    }

    @Test
    void 댓글_삭제_성공(){
        //given
        String content = "This is a test comment.";
        Comment comment = Comment.createComment(user, board, content);

        // When
        comment.delete();

        assertThat(comment.getStatus()).isEqualTo(DELETED);
    }

    @ParameterizedTest
    @EnumSource(value = CommentStatus.class, names = {"DELETED"}, mode = INCLUDE)
    void 댓글_삭제_실패_이미_삭제된_상태(CommentStatus status){
        String content = "This is a test comment.";
        Comment comment = Comment.createComment(user, board, content);

        comment.setStatus(status);

        assertThatIllegalStateException().isThrownBy(() -> comment.delete());
    }
}