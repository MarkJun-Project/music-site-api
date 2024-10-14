package com.music.core.service.board;

import com.music.common.attachment.domain.Attachment;
import com.music.common.attachment.service.AttachmentService;
import com.music.common.board.domain.Board;
import com.music.common.board.service.BoardService;
import com.music.common.code.MusicCategory;
import com.music.integration.storege.FileStore;
import com.music.integration.storege.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultBoardAppService implements BoardAppService{

    private final FileStore fileStore;
    private final BoardService boardService;
    private final AttachmentService attachmentService;

    @Override
    public void create(Long userId, String title, String description, String songTitle, MusicCategory category, MultipartFile multipartFile) {
        UploadFile uploadFile = fileStore.upload(multipartFile, Board.DIR_NAME);
        Attachment attachment = attachmentService.create(uploadFile.getFileUrl(), uploadFile.getFileName(), uploadFile.getOriginFileName());
        boardService.create(userId, attachment.getId(), title, description, songTitle, category);
    }
}
