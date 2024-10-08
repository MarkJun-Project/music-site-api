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
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

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

    @Test
    void 유저신고_관리자_검토_시작_성공() {
        // given
        ReportCategory category = ReportCategory.DISTURBING_CONTENT;
        String reason = "This is a report.";

        Report report = Report.create(reporter, reportedUser, category, reason);

        // when
        report.startReview(admin);

        // then
        Assertions.assertThat(report.getAdmin()).isEqualTo(admin);
        Assertions.assertThat(report.getStatus()).isEqualTo(ReportStatus.UNDER_REVIEW);
    }

    @Test
    void 유저신고_관리자_검토_시작_실패_어드민_null() {
        // given
        ReportCategory category = ReportCategory.DISTURBING_CONTENT;
        String reason = "This is a report.";

        Report report = Report.create(reporter, reportedUser, category, reason);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> report.startReview(null));
    }

    @Test
    void 유저신고_상태_검토전이_아님() {
        // given
        ReportCategory category = ReportCategory.DISTURBING_CONTENT;
        String reason = "This is a report.";

        Report report = Report.create(reporter, reportedUser, category, reason);
        report.startReview(admin);

        // when & then
        assertThatIllegalStateException()
                .isThrownBy(() -> report.startReview(admin));
    }
}
