package com.music.common.musicscore.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicScoreRepository extends JpaRepository<MusicScore, Long> {
    boolean existsByUserIdAndBoardId(Long userId, Long boardId);
}
