package com.music.common.admin.domian;


import fixtures.AdminFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    void 관리자_서비스_아이디_이미_존재함() {
        // given
        Admin admin = AdminFixture.create();
        adminRepository.save(admin);

        // when
        boolean exists = adminRepository.existsByServiceId(admin.getServiceId());

        // then
        assertThat(exists).isTrue();
    }

    @Test
    void 관리자_서비스_아이디_존재하지_않음() {
        // given
        String serviceId = "serviceId";

        // when
        boolean exists = adminRepository.existsByServiceId(serviceId);

        // then
        assertThat(exists).isFalse();
    }
}