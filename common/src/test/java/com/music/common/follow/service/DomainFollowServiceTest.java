package com.music.common.follow.service;


import com.music.common.follow.domain.Follow;
import com.music.common.follow.domain.FollowRepository;
import com.music.common.support.BaseServiceTest;
import com.music.common.support.CustomException;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.support.ErrorCode.ALREADY_FOLLOW;
import static com.music.common.support.ExceptionTest.assertThatActorValidateCustomException;
import static org.assertj.core.api.Assertions.*;

class DomainFollowServiceTest extends BaseServiceTest {

    @Autowired
    private DomainFollowService domainFollowService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

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

    @Test
    void 팔로우_삭제_실패_팔로우_누른_유저가_아님() {
        // given
        Follow follow = domainFollowService.create(follower.getId(), followee.getId());
        User fakeUser = userRepository.save(UserFixture.create());

        // when & then
        assertThatActorValidateCustomException()
                .isThrownBy(() -> domainFollowService.delete(fakeUser.getId(), follow.getId()));
    }

    @Test
    void 팔로우_삭제_성공() {
        // given
        Follow follow = domainFollowService.create(follower.getId(), followee.getId());

        // when
        domainFollowService.delete(follower.getId(), follow.getId());

        // then
        assertThat(followRepository.findById(follow.getId()).isEmpty()).isTrue();
    }
}