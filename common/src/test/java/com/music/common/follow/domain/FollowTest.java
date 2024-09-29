package com.music.common.follow.domain;


import com.music.common.user.domain.User;
import fixtures.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class FollowTest {

    private User follower;
    private User followee;

    @BeforeEach
    void setUp() {
        this.follower = UserFixture.create();
        this.followee = UserFixture.create();
    }

    @Test
    void 팔로우_생성_성공() {
        // given
        User follower = this.follower;
        User followee = this.followee;

        // when
        Follow follow = Follow.create(follower, followee);

        // then
        Assertions.assertThat(follow).isNotNull();
        Assertions.assertThat(follow.getFollower()).isEqualTo(follower);
        Assertions.assertThat(follow.getFollowee()).isEqualTo(followee);
    }

    @Test
    void 팔로우_생성_실패_팔로워_null() {
        // Given
        User followee = this.followee;

        // When & Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Follow.create(null, followee));
    }

    @Test
    void 팔로우_생성_실패_팔로우된_유저_null() {
        // Given
        User follower = this.follower;

        // When & Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Follow.create(follower, null));
    }

    @Test
    void 팔로우_생성_실패_팔로워_팔로우된_유저_모두_null() {
        // Given

        // When & Then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Follow.create(null, null));
    }
}
