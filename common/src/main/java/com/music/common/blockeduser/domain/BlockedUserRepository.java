package com.music.common.blockeduser.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
    boolean existsByBlockerIdAndBlockedId(Long blockerId, Long blockedId);
}
