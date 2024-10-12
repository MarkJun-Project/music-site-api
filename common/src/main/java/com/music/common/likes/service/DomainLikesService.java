package com.music.common.likes.service;

import com.music.common.board.domain.BoardRepository;
import com.music.common.likes.domain.Likes;
import com.music.common.likes.domain.LikesRepository;
import com.music.common.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainLikesService implements LikesService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;

    @Override
    public Likes create(Long userId, Long BoardId) {
        val user = userRepository.findById(userId).orElseThrow();
        val board = boardRepository.findById(BoardId).orElseThrow();

        val likes = Likes.create(user, board);

        return likesRepository.save(likes);
    }
}
