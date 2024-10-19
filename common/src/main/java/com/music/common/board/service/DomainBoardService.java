package com.music.common.board.service;

import com.music.common.attachment.domain.AttachmentRepository;
import com.music.common.board.domain.Board;
import com.music.common.board.domain.BoardRepository;
import com.music.common.code.MusicCategory;
import com.music.common.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.music.common.support.Preconditions.*;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainBoardService implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public Board create(
            Long userId, Long attachmentId, String title, String description, String songTitle, MusicCategory category
    ) {
        val user = userRepository.findById(userId).orElseThrow();
        val attachment = attachmentRepository.findById(attachmentId).orElseThrow();

        val board = Board.create(user, attachment, title, description, songTitle, category);

        return boardRepository.save(board);
    }

    @Override
    public void update(Long boardId, Long userId, String title, String description) {
        val board = boardRepository.findById(boardId).orElseThrow();
        val user = userRepository.findById(userId).orElseThrow();

        actorValidate(board.isUser(user));

        board.update(title, description);
    }

    @Override
    public void delete(Long BoardId, Long userId) {
        val board = boardRepository.findById(BoardId).orElseThrow();
        val user = userRepository.findById(userId).orElseThrow();

        actorValidate(board.isUser(user));

        board.delete();
    }
}
