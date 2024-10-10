package com.music.common.user.service;


import com.music.common.code.SocialType;
import com.music.common.support.BaseServiceTest;
import com.music.common.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class DomainUserServiceTest extends BaseServiceTest {

    @Autowired
    private DomainUserService domainUserService;

    @Test
    void 유저_생성_성공() {
        // given

        // when
        User user = domainUserService.loadUser("socialId", SocialType.KAKAO, "email@email.com");

        // then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getSocialId()).isEqualTo("socialId");
        assertThat(user.getSocialType()).isEqualTo(SocialType.KAKAO);
        assertThat(user.getEmail()).isEqualTo("email@email.com");
    }

    @Test
    void 중복_회원_저장_x() {
        // given
        User existingUser = domainUserService.loadUser("socialId", SocialType.KAKAO, "email@email.com");

        // when
        User loadedUser = domainUserService.loadUser("socialId", SocialType.KAKAO, "email@email.com");

        // then
        assertThat(existingUser.getId()).isEqualTo(loadedUser.getId());
    }
}