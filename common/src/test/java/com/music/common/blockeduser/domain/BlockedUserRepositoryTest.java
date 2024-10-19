package com.music.common.blockeduser.domain;


import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BlockedUserRepositoryTest {

    @Autowired
    private BlockedUserRepository blockedUserRepository;

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
    void 이미_차단된_관계() {
        // given
        blockedUserRepository.save(BlockedUser.create(blocker, blocked));

        // when
        boolean exists = blockedUserRepository.existsByBlockerIdAndBlockedId(blocker.getId(), blocked.getId());

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 차단되지_않은_관계() {
        // given

        // when
        boolean exists = blockedUserRepository.existsByBlockerIdAndBlockedId(blocker.getId(), blocked.getId());

        // then
        assertThat(exists).isFalse();
    }
}