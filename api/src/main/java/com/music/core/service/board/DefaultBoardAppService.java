package com.music.core.service.board;

import com.music.common.board.domain.Board;
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

    @Override
    public UploadFile fileUpload(MultipartFile multipartFile) {
        return fileStore.upload(multipartFile, Board.DIR_NAME);
    }
}
