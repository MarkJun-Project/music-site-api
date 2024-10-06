package com.music.common.notice.domian;


import com.music.common.attachment.domain.Attachment;
import fixtures.AdminFixture;
import fixtures.AttachmentFixture;
import fixtures.NoticeFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class NoticeAttachmentTest {

    private Attachment attachment;
    private Notice notice;

    @BeforeEach
    void setUp() {
        this.attachment = AttachmentFixture.create(); // Attachment 객체 생성
        this.notice = NoticeFixture.create();
    }

    @Test
    void 공지사항에_첨부파일_추가_성공() {
        // when
        NoticeAttachment noticeAttachment = NoticeAttachment.create(attachment, notice);

        // then
        Assertions.assertThat(noticeAttachment).isNotNull();
        Assertions.assertThat(noticeAttachment.getAttachment()).isEqualTo(attachment);
        Assertions.assertThat(noticeAttachment.getNotice()).isEqualTo(notice);
        Assertions.assertThat(notice.getAttachments()).contains(noticeAttachment);
        Assertions.assertThat(notice.getAttachments()).hasSize(1);
    }

    @Test
    void 공지사항에_다수의_첨부파일_추가_성공() {
        // given
        Attachment attachment1 = attachment;
        Attachment attachment2 = AttachmentFixture.create();

        // when
        NoticeAttachment noticeAttachment1 = NoticeAttachment.create(attachment1, notice);
        NoticeAttachment noticeAttachment2 = NoticeAttachment.create(attachment2, notice);

        // then
        Assertions.assertThat(notice.getAttachments()).containsExactlyInAnyOrder(noticeAttachment1, noticeAttachment2);
        Assertions.assertThat(notice.getAttachments()).hasSize(2);
    }

    @Test
    void 공지사항첨부파일_생성_실패_첨부파일_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> NoticeAttachment.create(null, notice));
    }

    @Test
    void 공지사항첨부파일_생성_실패_공지사항_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> NoticeAttachment.create(attachment, null));
    }

    @Test
    void 공지사항첨부파일_생성_실패_첨부파일과_공지사항_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> NoticeAttachment.create(null, null));
    }

}