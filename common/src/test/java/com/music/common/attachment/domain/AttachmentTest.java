package com.music.common.attachment.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class AttachmentTest {

    @Test
    void 첨부파일_생성_성공() {
        // given
        String fileUrl = "https://example.com/file.jpg";
        String fileName = "file.jpg";
        String originFileName = "original_file.jpg";

        // when
        Attachment attachment = Attachment.create(fileUrl, fileName, originFileName);

        // then
        assertThat(attachment.getFileUrl()).isEqualTo(fileUrl);
        assertThat(attachment.getFileName()).isEqualTo(fileName);
        assertThat(attachment.getOriginFileName()).isEqualTo(originFileName);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 첨부파일_생성_실패_파일URL_값_null_혹은_빈값(String invalidFileUrl) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Attachment.create(invalidFileUrl, "fileName", "originFileName"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 첨부파일_생성_실패_파일명_값_null_혹은_빈값(String invalidFileName) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Attachment.create("fileUrl", invalidFileName, "originFileName"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 첨부파일_생성_실패_원본파일명_값_null_혹은_빈값(String invalidOriginFileName) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Attachment.create("fileUrl", "fileName", invalidOriginFileName));
    }
}
