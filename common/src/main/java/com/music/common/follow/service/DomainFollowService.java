package com.music.common.follow.service;

import com.music.common.follow.domain.Follow;
import com.music.common.follow.domain.FollowRepository;
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
public class DomainFollowService implements FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Override
    public Follow create(Long followerId, Long followeeId) {
        validate(!followRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId), ALREADY_FOLLOW);

        val follower = userRepository.findById(followerId).orElseThrow();
        val followee = userRepository.findById(followeeId).orElseThrow();

        val follow = Follow.create(follower, followee);

        return followRepository.save(follow);
    }

    @Override
    public void delete(Long followerId, Long followId) {
        val follower = userRepository.findById(followerId).orElseThrow();
        val follow = followRepository.findById(followId).orElseThrow();

        actorValidate(follow.isUser(follower));

        followRepository.delete(follow);
    }
}
