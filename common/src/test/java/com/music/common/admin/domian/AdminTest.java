package com.music.common.admin.domian;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AdminTest {
    @Test
    void 어드민_생성_성공() {
        // given
        String serviceId = "admin123";
        String password = "password123";

        // when
        Admin admin = Admin.create(serviceId, password);

        // then
        Assertions.assertThat(admin).isNotNull();
        Assertions.assertThat(admin.getServiceId()).isEqualTo(serviceId);
        Assertions.assertThat(admin.getPassword()).isEqualTo(password);
        Assertions.assertThat(admin.getStatus()).isEqualTo(AdminStatus.CREATED);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 어드민_생성_실패_서비스ID_null_혹은_빈값(String invalidServiceId) {
        // given

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Admin.create(invalidServiceId, "password123"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 어드민_생성_실패_비밀번호_null_혹은_빈값(String invalidPassword) {
        // given

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Admin.create("admin123", invalidPassword));
    }
}
