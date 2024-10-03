package com.music.common.notice.domian;


import com.music.common.admin.domian.Admin;
import fixtures.AdminFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class NoticeTest {
    private Admin admin;

    @BeforeEach
    void setUp() {
        this.admin = AdminFixture.create();
    }

    @Test
    void 공지사항_생성_성공() {
        // given
        String title = "Test Notice Title";
        String description = "This is a test description for the notice.";

        // when
        Notice notice = Notice.create(admin, title, description);

        // then
        Assertions.assertThat(notice).isNotNull();
        Assertions.assertThat(notice.getAdmin()).isEqualTo(admin);
        Assertions.assertThat(notice.getTitle()).isEqualTo(title);
        Assertions.assertThat(notice.getDescription()).isEqualTo(description);
        Assertions.assertThat(notice.getStatus()).isEqualTo(NoticeStatus.CREATED);
    }

    @Test
    void 공지사항_생성_실패_관리자_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Notice.create(null, "Test Title", "Description"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_생성_실패_제목_null_혹은_빈값(String invalidTitle) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Notice.create(admin, invalidTitle, "Description"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 공지사항_생성_실패_설명_null_혹은_빈값(String invalidDescription) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Notice.create(admin, "Test Title", invalidDescription));
    }
}