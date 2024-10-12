package com.music.common.likes.service;

import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.likes.domain.Likes;
import com.music.common.likes.domain.LikesRepository;
import com.music.common.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.music.common.support.ErrorCode.*;
import static com.music.common.support.Preconditions.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainLikesService implements LikesService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;

    @Override
    public Likes create(Long userId, Long boardId) {
        validate(!likesRepository.existsByUserIdAndBoardId(userId, boardId), ALREADY_LIKED);

        val user = userRepository.findById(userId).orElseThrow();
        val board = boardRepository.findById(boardId).orElseThrow();

        val likes = Likes.create(user, board);

        return likesRepository.save(likes);
    }

    @Override
    public void delete(Long likesId, Long userId) {
        val likes = likesRepository.findById(likesId).orElseThrow();
        val user = userRepository.findById(userId).orElseThrow();

        actorValidate(likes.isUser(user));

        likesRepository.delete(likes);

        Board board = boardRepository.findById(likes.getBoard().getId()).orElseThrow();
        board.getLikes().remove(likes);
    }
}
