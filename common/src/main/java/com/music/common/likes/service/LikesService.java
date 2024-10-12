package com.music.common.likes.service;

import com.music.common.likes.domain.Likes;

public interface LikesService {
    Likes create(Long userId, Long BoardId);
}
