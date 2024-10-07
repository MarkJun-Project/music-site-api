package com.music.common.report.domain;


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

class ReportTest {
    private User reporter;
    private User reportedUser;
    private Admin admin;

    @BeforeEach
    void setUp() {
        this.reporter = UserFixture.create();
        this.reportedUser = UserFixture.create();
        this.admin = AdminFixture.create();
    }

    @Test
    void 유저신고_생성_성공() {
        // given
        ReportCategory category = ReportCategory.DISTURBING_CONTENT;
        String reason = "This is a report.";

        // when
        Report report = Report.create(reporter, reportedUser, category, reason);

        // then
        Assertions.assertThat(report).isNotNull();
        Assertions.assertThat(report.getReporter()).isEqualTo(reporter);
        Assertions.assertThat(report.getReportedUser()).isEqualTo(reportedUser);
        Assertions.assertThat(report.getCategory()).isEqualTo(category);
        Assertions.assertThat(report.getReason()).isEqualTo(reason);
        Assertions.assertThat(report.getStatus()).isEqualTo(ReportStatus.PENDING);
    }

    @Test
    void 유저신고_생성_실패_신고자_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Report.create(null, reportedUser, ReportCategory.DISTURBING_CONTENT, "Reason"));
    }

    @Test
    void 유저신고_생성_실패_신고대상_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Report.create(reporter, null, ReportCategory.DISTURBING_CONTENT, "Reason"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 유저신고_생성_실패_사유_null_혹은_빈값(String invalidReason) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Report.create(reporter, reportedUser, ReportCategory.DISTURBING_CONTENT, invalidReason));
    }

    @Test
    void 유저신고_생성_실패_카테고리_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Report.create(reporter, reportedUser, null, "Reason"));
    }
}
