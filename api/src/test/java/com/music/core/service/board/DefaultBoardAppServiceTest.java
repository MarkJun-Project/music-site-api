package com.music.core.service.board;

import com.music.common.board.domain.Board;
import com.music.core.support.BaseServiceTest;
import com.music.integration.storege.UploadFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBoardAppServiceTest extends BaseServiceTest {

    @Autowired
    private DefaultBoardAppService defaultBoardAppService;

    @Test
    void 파일_업로드_성공() {
        // given
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testvideo.mp4",
                "video/mp4",
                new byte[]{  }
        );

        // when
        UploadFile uploadFile = defaultBoardAppService.fileUpload(mockFile);

        // then
        assertThat(uploadFile).isNotNull();
        assertThat(uploadFile.getFileName()).isNotBlank().isNotNull().isNotEmpty();
        assertThat(uploadFile.getOriginFileName()).isEqualTo(mockFile.getOriginalFilename());
        assertThat(uploadFile.getFileUrl()).isEqualTo(Board.DIR_NAME+ File.separator + uploadFile.getFileName());
    }
}