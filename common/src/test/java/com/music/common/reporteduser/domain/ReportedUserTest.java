package com.music.common.reporteduser.domain;


import com.music.common.admin.domian.Admin;
import com.music.common.user.domain.User;
import fixtures.AdminFixture;
import fixtures.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ReportedUserTest {
    private User reporter;
    private User reported;
    private Admin admin;

    @BeforeEach
    void setUp() {
        this.reporter = UserFixture.create();
        this.reported = UserFixture.create();
        this.admin = AdminFixture.create();
    }

    @Test
    void 유저신고_생성_성공() {
        // given
        ReportCategory category = ReportCategory.DISTURBING_CONTENT;
        String reason = "This is a report.";

        // when
        ReportedUser reportedUser = ReportedUser.create(reporter, reported, admin, category, reason);

        // then
        Assertions.assertThat(reportedUser).isNotNull();
        Assertions.assertThat(reportedUser.getReporter()).isEqualTo(reporter);
        Assertions.assertThat(reportedUser.getReported()).isEqualTo(reported);
        Assertions.assertThat(reportedUser.getAdmin()).isEqualTo(admin);
        Assertions.assertThat(reportedUser.getCategory()).isEqualTo(category);
        Assertions.assertThat(reportedUser.getReason()).isEqualTo(reason);
        Assertions.assertThat(reportedUser.getStatus()).isEqualTo(ReportStatus.PENDING);
    }

    @Test
    void 유저신고_생성_실패_신고자_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ReportedUser.create(null, reported, admin, ReportCategory.DISTURBING_CONTENT, "Reason"));
    }

    @Test
    void 유저신고_생성_실패_신고대상_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ReportedUser.create(reporter, null, admin, ReportCategory.DISTURBING_CONTENT, "Reason"));
    }

    @Test
    void 유저신고_생성_실패_관리자_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ReportedUser.create(reporter, reported, null, ReportCategory.DISTURBING_CONTENT, "Reason"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 유저신고_생성_실패_사유_null_혹은_빈값(String invalidReason) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ReportedUser.create(reporter, reported, admin, ReportCategory.DISTURBING_CONTENT, invalidReason));
    }

    @Test
    void 유저신고_생성_실패_카테고리_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> ReportedUser.create(reporter, reported, admin, null, "Reason"));
    }
}