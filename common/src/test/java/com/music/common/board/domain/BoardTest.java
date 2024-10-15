package com.music.common.board.domain;


import com.music.common.attachment.domain.Attachment;
import com.music.common.code.MusicCategory;
import com.music.common.user.domain.User;
import fixtures.AttachmentFixture;
import fixtures.BoardFixture;
import fixtures.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.music.common.board.domain.BoardStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class BoardTest {

    private User user;
    private Attachment file;

    @BeforeEach
    void setUp() {
        this.user = UserFixture.create();
        this.file = AttachmentFixture.create();
    }

    @Test
    void 게시글_생성_성공() {
        // Given: 게시글 생성에 필요한 데이터를 준비
        String title = "Test Title";
        String description = "This is a test description for the board.";
        String songTitle = "Test Song";
        MusicCategory musicCategory = MusicCategory.COUNTRY;

        // When: Board 객체를 생성
        Board board = Board.create(user, file, title, description, songTitle, musicCategory);

        // Then: 생성된 Board 객체의 필드가 예상대로 설정되었는지 검증
        assertThat(board).isNotNull();
        assertThat(board.getUser()).isEqualTo(user);
        assertThat(board.getFile()).isEqualTo(file);
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getDescription()).isEqualTo(description);
        assertThat(board.getSongTitle()).isEqualTo(songTitle);
        assertThat(board.getMusicCategory()).isEqualTo(musicCategory);
        assertThat(board.getStatus()).isEqualTo(CREATED); // 기본 상태는 CREATED여야 합니다.
    }

    @Test
    void 게시글_생성_실패_유저_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Board.create(null, file, "Test Title", "Description", "Song Title", MusicCategory.COUNTRY));
    }

    @Test
    void 게시글_생성_실패_파일_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Board.create(user, null, "Test Title", "Description", "Song Title", MusicCategory.COUNTRY));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 게시글_생성_실패_제목_null_혹은_빈값(String invalidTitle) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Board.create(user, file, invalidTitle, "Description", "Song Title", MusicCategory.COUNTRY));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 게시글_생성_실패_설명_null_혹은_빈값(String invalidDescription) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Board.create(user, file, "Test Title", invalidDescription, "Song Title", MusicCategory.COUNTRY));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 게시글_생성_실패_노래제목_null_혹은_빈값(String invalidSongTitle) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Board.create(user, file, "Test Title", "Description", invalidSongTitle, MusicCategory.COUNTRY));
    }

    @Test
    void 게시글_생성_실패_카테고리_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Board.create(user, file, "Test Title", "Description", "Song Title", null));
    }

    @Test
    void 게시글_삭제_성공(){
        //given
        Board board = BoardFixture.create();

        // When
        board.delete();

        // then
        assertThat(board.getStatus()).isEqualTo(DELETED);
    }

    @Test
    void 게시글_삭제_실패_이미_삭제됨(){
        // given
        Board board = BoardFixture.create();
        board.delete();

        // when & then
        assertThatIllegalStateException().isThrownBy(board::delete);
    }
}