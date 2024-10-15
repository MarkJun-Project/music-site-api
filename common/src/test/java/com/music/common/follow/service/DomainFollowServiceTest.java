package com.music.common.follow.service;


import com.music.common.follow.domain.Follow;
import com.music.common.support.BaseServiceTest;
import com.music.common.support.CustomException;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.support.ErrorCode.ALREADY_FOLLOW;
import static org.assertj.core.api.Assertions.*;

class DomainFollowServiceTest extends BaseServiceTest {

    @Autowired
    private DomainFollowService domainFollowService;

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
    void 팔로우_생성_실패_이미_팔로우한_유저() {
        // given
        domainFollowService.create(follower.getId(), followee.getId());

        // when & then
        assertThatThrownBy(() -> domainFollowService.create(follower.getId(), followee.getId()))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ALREADY_FOLLOW);
    }

    @Test
    void 팔로우_생성_성공() {
        // given

        // when
        Follow follow = domainFollowService.create(follower.getId(), followee.getId());

        // then
        assertThat(follow.getId()).isNotNull();
        assertThat(follow.getFollower().getId()).isEqualTo(follower.getId());
        assertThat(follow.getFollowee().getId()).isEqualTo(followee.getId());
    }
}