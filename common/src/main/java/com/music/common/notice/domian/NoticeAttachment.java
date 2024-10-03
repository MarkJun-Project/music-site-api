package com.music.common.notice.domian;

import com.music.common.attachment.domain.Attachment;
import com.music.common.support.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.music.common.support.Preconditions.require;
import static java.util.Objects.nonNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class NoticeAttachment extends BaseEntity {

    @JoinColumn(name = "ATTACHMENT_ID", nullable = false)
    @ManyToOne
    private Attachment attachment;

    @Column(name = "NOTICE_ID", nullable = false)
    @ManyToOne
    private Notice notice;

    private NoticeAttachment(Attachment attachment, Notice notice) {
        notice.addAttachment(this);

        this.attachment = attachment;
        this.notice = notice;
    }

    public static NoticeAttachment create(Attachment attachment, Notice notice) {
        require(nonNull(attachment));
        require(nonNull(notice));

        return new NoticeAttachment(attachment, notice);
    }
}
