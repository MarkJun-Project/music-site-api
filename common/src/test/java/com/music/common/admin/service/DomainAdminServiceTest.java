package com.music.common.admin.service;


import com.music.common.admin.domian.Admin;
import com.music.common.support.BaseServiceTest;
import com.music.common.support.CustomException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.music.common.admin.domian.AdminStatus.*;
import static com.music.common.support.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;

class DomainAdminServiceTest extends BaseServiceTest {

    @Autowired
    private DomainAdminService domainAdminService;

    @Test
    void 관리자_생성_실패_중복된_아이디() {
        // given
        String serviceId = "serviceId";
        String password = "password";
        domainAdminService.create(serviceId, password);

        // when & then
        assertThatThrownBy(() -> domainAdminService.create(serviceId, password))
                .isInstanceOf(CustomException.class)
                .hasFieldOrPropertyWithValue("errorCode", ADMIN_SERVICE_ID_ALREADY_EXISTS);
    }

    @Test
    void 관리자_생성_성공() {
        // given
        String serviceId = "serviceId";
        String password = "password";

        // when
        Admin createdAdmin = domainAdminService.create(serviceId, password);

        // then
        assertThat(createdAdmin.getId()).isNotNull();
        assertThat(createdAdmin.getServiceId()).isEqualTo(serviceId);
        assertThat(createdAdmin.getPassword()).isEqualTo(password);
        assertThat(createdAdmin.getStatus()).isEqualTo(CREATED);
    }
}