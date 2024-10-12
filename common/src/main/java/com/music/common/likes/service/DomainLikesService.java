package com.music.common.likes.service;

import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.likes.domain.Likes;
import com.music.common.likes.domain.LikesRepository;
import com.music.common.user.domain.User;
import com.music.common.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
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
        User user = userRepository.findById(userId).orElseThrow();
        Board board = boardRepository.findById(BoardId).orElseThrow();

        Likes likes = Likes.create(user, board);

        return likesRepository.save(likes);
    }
}
