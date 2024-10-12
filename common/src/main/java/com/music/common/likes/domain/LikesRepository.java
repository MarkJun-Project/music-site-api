package com.music.common.likes.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByUserIdAndBoardId(Long userId, Long boardId);
}
