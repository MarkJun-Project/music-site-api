package com.music.common.follow.domain;

import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FollowRepositoryTest {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    private User follower;
    private User followee;

    @BeforeEach
    void setUp() {
        follower = userRepository.save(UserFixture.create());
        followee = userRepository.save(UserFixture.create());
    }

    @Test
    void 팔로우_관계가_존재하지_않음() {
        // given

        // when
        boolean exists = followRepository.existsByFollowerIdAndFolloweeId(follower.getId(), followee.getId());

        // then
        assertThat(exists).isFalse();
    }

    @Test
    void 팔로우_관계가_존재함() {
        // given
        followRepository.save(Follow.create(follower, followee));

        // when
        boolean exists = followRepository.existsByFollowerIdAndFolloweeId(follower.getId(), followee.getId());

        // then
        assertThat(exists).isTrue();
    }
}