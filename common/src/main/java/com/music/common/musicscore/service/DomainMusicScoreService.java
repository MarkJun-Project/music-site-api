package com.music.common.musicscore.service;

import com.music.common.board.domain.BoardRepository;
import com.music.common.musicscore.domain.MusicScore;
import com.music.common.musicscore.domain.MusicScoreRepository;
import com.music.common.musicscore.domain.Score;
import com.music.common.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.music.common.support.Preconditions.check;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainMusicScoreService implements MusicScoreService{

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final MusicScoreRepository musicScoreRepository;

    @Override
    public MusicScore create(Long userId, Long boardId, Score score) {
        check(!musicScoreRepository.existsByUserIdAndBoardId(userId,boardId), "이미 평가한 게시글입니다.");

        val user = userRepository.findById(userId).orElseThrow();
        val board = boardRepository.findById(boardId).orElseThrow();

        val musicScore = MusicScore.create(user, board, score);

        return musicScoreRepository.save(musicScore);
    }
}
