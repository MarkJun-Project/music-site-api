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
}
