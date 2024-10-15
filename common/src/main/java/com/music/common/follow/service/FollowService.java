package com.music.common.follow.service;

import com.music.common.follow.domain.Follow;

public interface FollowService {
    Follow create(Long followerId, Long followeeId);
}
