package com.music.common.blockeduser.domain;


import com.music.common.user.domain.User;
import fixtures.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BlockedUserTest {
    private User blocker;
    private User blocked;

    @BeforeEach
    void setUp() {
        this.blocker = UserFixture.create();
        this.blocked = UserFixture.create();
    }

    @Test
    void 차단_생성_성공() {
        // given
        User blocker = this.blocker;
        User blocked = this.blocked;

        // when
        BlockedUser blockedUser = BlockedUser.create(blocker, blocked);

        // then
        Assertions.assertThat(blockedUser).isNotNull();
        Assertions.assertThat(blockedUser.getBlocker()).isEqualTo(blocker);
        Assertions.assertThat(blockedUser.getBlocked()).isEqualTo(blocked);
    }

    @Test
    void 차단_생성_실패_차단한_사용자_null() {
        // Given
        User blocked = this.blocked;

        // When & Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BlockedUser.create(null, blocked));
    }

    @Test
    void 차단_생성_실패_차단된_사용자_null() {
        // Given
        User blocker = this.blocker;

        // When & Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BlockedUser.create(blocker, null));
    }

    @Test
    void 차단_생성_실패_차단한_사용자_차단된_사용자_모두_null() {
        // Given

        // When & Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> BlockedUser.create(null, null));
    }
}
