package com.music.core.service.attachment;


import com.music.core.support.BaseServiceTest;
import com.music.integration.storege.UploadFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultAttachmentAppServiceTest extends BaseServiceTest {
    @Autowired
    private DefaultAttachmentAppService defaultAttachmentAppService;

    @Test
    void 파일_업로드_테스트() {
        // given
        String dirName = "Board";

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testvideo.mp4",
                "video/mp4",
                new byte[]{  }
        );

        // when
        UploadFile actualUploadFile = defaultAttachmentAppService.fileUpload(mockFile, dirName);

        // then
        assertThat(actualUploadFile).isNotNull();
        assertThat(actualUploadFile.getFileName()).isNotBlank().isNotNull().isNotEmpty();
        assertThat(actualUploadFile.getOriginFileName()).isEqualTo(mockFile.getOriginalFilename());
        assertThat(actualUploadFile.getFileUrl()).isEqualTo(dirName+ File.separator + actualUploadFile.getFileName());
    }
}
