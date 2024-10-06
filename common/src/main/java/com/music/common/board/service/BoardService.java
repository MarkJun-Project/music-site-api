package com.music.common.board.service;

import com.music.common.board.domain.Board;
import com.music.common.code.MusicCategory;

public interface BoardService {
    Board create(Long userId, Long attachmentId, String title, String description, String songTitle, MusicCategory category);
}
