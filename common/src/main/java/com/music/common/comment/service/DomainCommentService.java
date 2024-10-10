package com.music.common.comment.service;


import com.music.common.comment.domain.Comment;
import com.music.common.comment.domain.CommentRepository;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.music.common.support.Preconditions.actorValidate;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainCommentService implements CommentService {

    private CommentRepository commentRepository;

    private UserRepository userRepository;
    @Override
    public void update(Long commentId, Long userId, String content) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        User user = userRepository.findById(userId).orElseThrow();

        actorValidate(comment.isUser(user));

        comment.update(comment);
    }
}
