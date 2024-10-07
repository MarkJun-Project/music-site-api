package com.music.common.report.domain;

import lombok.Getter;

@Getter
public enum ReportStatus {
    PENDING("대기 중"),
    UNDER_REVIEW("검토 중"),
    PROCESSED("처리 완료"),
    REJECTED("거부됨");

    private final String description;

    ReportStatus(String description) {
        this.description = description;
    }
}
