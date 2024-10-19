package com.music.common.blockeduser.service;


import com.music.common.blockeduser.domain.BlockedUser;
import com.music.common.support.BaseServiceTest;
import com.music.common.support.CustomException;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.support.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;

class DomainBlockedUserServiceTest extends BaseServiceTest {

    @Autowired
    private DomainBlockedUserService domainBlockedUserService;

    @Autowired
    private UserRepository userRepository;


    private User blocker;
    private User blocked;

    @BeforeEach
    void setUp() {
        blocker = userRepository.save(UserFixture.create());
        blocked = userRepository.save(UserFixture.create());
    }

    @Test
    void 차단_생성_실패_이미_차단한_유저() {
        // given
        domainBlockedUserService.create(blocker.getId(), blocked.getId());

        // when & then
        assertThatThrownBy(() -> domainBlockedUserService.create(blocker.getId(), blocked.getId()))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", BLOCK_ALREADY_EXISTED);
    }

    @Test
    void 차단_생성_성공() {
        // given

        // when
        BlockedUser block = domainBlockedUserService.create(blocker.getId(), blocked.getId());

        // then
        assertThat(block.getId()).isNotNull();
        assertThat(block.getBlocker().getId()).isEqualTo(blocker.getId());
        assertThat(block.getBlocked().getId()).isEqualTo(blocked.getId());
    }
}