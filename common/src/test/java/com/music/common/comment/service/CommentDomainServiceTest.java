package com.music.common.comment.service;

import com.music.common.board.domain.Board;
import com.music.common.comment.domain.Comment;
import com.music.common.comment.domain.CommentRepository;
import com.music.common.user.domain.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class CommentDomainServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    private User user;

    private Board board;

    private Comment comment;

    @Test
    void 스터디_댓글_수정_성공() {
        studyComment = CommentRepository.save(c(study, leader));

        studyCommentService.update(studyComment.getId(), leader.getId(), "updatedComment");

        assertThat(studyComment.getComment()).isEqualTo("updatedComment");
    }

    @Test
    void 스터디_댓글_수정_실패__작성자가_아님() {
        studyComment = studyCommentRepository.save(createStudyComment(study, leader));

        User fakeMember = userRepository.save(createActivateUser("fakeMember"));

        assertThatActorValidateCodeLapException().isThrownBy(() -> studyCommentService.update(studyComment.getId(), fakeMember.getId(), "updatedComment"));
    }
}
