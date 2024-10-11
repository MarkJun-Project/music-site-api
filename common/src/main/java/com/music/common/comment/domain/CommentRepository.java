package com.music.common.comment.domain;

import com.music.common.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}
