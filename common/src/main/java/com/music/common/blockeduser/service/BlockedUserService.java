package com.music.common.blockeduser.service;

import com.music.common.blockeduser.domain.BlockedUser;

public interface BlockedUserService {
    BlockedUser create(Long blockerId, Long blockedId);
    void delete(Long blockId, Long blockedId);
}
