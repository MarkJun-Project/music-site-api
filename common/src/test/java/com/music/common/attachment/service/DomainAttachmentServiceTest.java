package com.music.common.attachment.service;


import com.music.common.attachment.domain.Attachment;
import com.music.common.support.BaseServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class DomainAttachmentServiceTest extends BaseServiceTest {

    @Autowired
    private DomainAttachmentService attachmentService;

    @Test
    void 첨부파일_생성_성공() {
        String fileUrl = "http://example.com/file";
        String fileName = "testFile.txt";
        String originFileName = "originalFileName.txt";

        Attachment createdAttachment = attachmentService.create(fileUrl, fileName, originFileName);

        assertThat(createdAttachment.getId()).isNotNull();
        assertThat(createdAttachment.getFileUrl()).isEqualTo(fileUrl);
        assertThat(createdAttachment.getFileName()).isEqualTo(fileName);
        assertThat(createdAttachment.getOriginFileName()).isEqualTo(originFileName);
    }
}