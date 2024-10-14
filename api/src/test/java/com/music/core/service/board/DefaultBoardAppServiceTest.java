package com.music.core.service.board;

import com.music.common.attachment.domain.Attachment;
import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.code.MusicCategory;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import com.music.core.support.BaseServiceTest;
import fixtures.UserFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class DefaultBoardAppServiceTest extends BaseServiceTest {

    @Autowired
    private DefaultBoardAppService defaultBoardAppService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void 게시글_관련_모든_처리_성공() {
        // given
        User user = userRepository.save(UserFixture.create());
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "testVideo.mp4",
                "video/mp4",
                new byte[]{  }
        );

        // when
        defaultBoardAppService.create(user.getId(), "title", "description", "songTitle", MusicCategory.COUNTRY, mockFile);
        Board board = boardRepository.findAll().get(0);

        // then
        assertThat(board.getId()).isNotNull();
        assertThat(board.getUser().getId()).isEqualTo(user.getId());
        assertThat(board.getFile().getFileName()).isEqualTo("randomName.mp4");
        assertThat(board.getFile().getFileUrl()).isEqualTo("Board" + File.separator + "testVideo.mp4");
        assertThat(board.getFile().getOriginFileName()).isEqualTo("testVideo.mp4");
    }
}