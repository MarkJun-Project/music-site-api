package com.music.common.comment.service;

import com.music.common.comment.domain.Comment;

public interface CommentService {
    Comment create(Long boardId, Long userId, String comment);

}
