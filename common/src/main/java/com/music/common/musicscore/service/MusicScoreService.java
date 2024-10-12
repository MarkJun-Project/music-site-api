package com.music.common.musicscore.service;

import com.music.common.musicscore.domain.MusicScore;
import com.music.common.musicscore.domain.Score;

public interface MusicScoreService {
    MusicScore create(Long userId, Long boardId, Score score);
}
