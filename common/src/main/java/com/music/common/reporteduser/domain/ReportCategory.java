package com.music.common.reporteduser.domain;

import lombok.Getter;

@Getter
public enum ReportCategory {
    INAPPROPRIATE_CONTENT("부적절한 콘텐츠"),
    FRAUD("사기 및 사기성 행동"),
    HATE_SPEECH("혐오 발언"),
    DISTURBING_CONTENT("불쾌감 유발"),
    UNMANNERLY_BEHAVIOR("비매너 행동"),
    ILLEGAL_ACTIVITY("불법 행위"),
    OTHER("기타");

    private final String description;

    ReportCategory(String description) {
        this.description = description;
    }
}
