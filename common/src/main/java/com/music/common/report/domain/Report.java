package com.music.common.report.domain;

import com.music.common.admin.domian.Admin;
import com.music.common.support.BaseEntity;
import com.music.common.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import static com.music.common.support.Preconditions.*;
import static java.util.Objects.nonNull;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report extends BaseEntity {
    @JoinColumn(name = "REPORTER_ID", nullable = false)
    @ManyToOne
    private User reporter;

    @JoinColumn(name = "REPORTED_USER_ID", nullable = false)
    @ManyToOne
    private User reportedUser;

    @JoinColumn(name = "ADMIN_ID")
    @ManyToOne
    private Admin admin;

    @Column(name = "CATEGORY", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportCategory category;

    @Column(name = "REASON", length = 1000, nullable = false)
    private String reason;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    private Report(User reporter, User reportedUser, ReportCategory category, String reason) {
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.category = category;
        this.reason = reason;
    }

    public static Report create(User reporter, User reportedUser, ReportCategory category, String reason) {
        require(nonNull(reporter));
        require(nonNull(reportedUser));
        require(nonNull(category));
        require(Strings.isNotBlank(reason));

        return new Report(reporter, reportedUser, category, reason);
    }

    public void startReview(Admin admin) {
        check(this.status == ReportStatus.PENDING);
        require(nonNull(admin));

        this.admin = admin;
        this.status = ReportStatus.UNDER_REVIEW;
    }
}
