package com.music.common.blockeduser.service;

import com.music.common.blockeduser.domain.BlockedUser;
import com.music.common.blockeduser.domain.BlockedUserRepository;

import com.music.common.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.music.common.support.ErrorCode.*;
import static com.music.common.support.Preconditions.actorValidate;
import static com.music.common.support.Preconditions.validate;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainBlockedUserService implements BlockedUserService{

    private final BlockedUserRepository blockedUserRepository;
    private final UserRepository userRepository;

    @Override
    public BlockedUser create(Long blockerId, Long blockedId) {
        validate(!blockedUserRepository.existsByBlockerIdAndBlockedId(blockerId, blockedId), BLOCK_ALREADY_EXISTED);

        val blocker = userRepository.findById(blockerId).orElseThrow();
        val blocked = userRepository.findById(blockedId).orElseThrow();

        val block = BlockedUser.create(blocker, blocked);

        return blockedUserRepository.save(block);
    }

    @Override
    public void delete(Long blockId, Long blockedId) {
        val blocker = userRepository.findById(blockedId).orElseThrow();
        val block = blockedUserRepository.findById(blockId).orElseThrow();

        actorValidate(block.isUser(blocker));

        blockedUserRepository.delete(block);
    }
}
