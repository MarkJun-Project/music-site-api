package com.music.common.comment.service;


import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.comment.domain.Comment;
import com.music.common.comment.domain.CommentRepository;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import static com.music.common.support.Preconditions.actorValidate;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainCommentService implements CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    @Override
    public Comment create(Long boardId, Long userId, String comment) {
        val board = boardRepository.findById(boardId).orElseThrow();
        val user = userRepository.findById(userId).orElseThrow();

        return commentRepository.save(Comment.createComment(user, board, comment));
    }

    @Override
    public void update(Long commentId, Long userId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        actorValidate(comment.isUser(user));

        comment.update(comment);
    }

}
