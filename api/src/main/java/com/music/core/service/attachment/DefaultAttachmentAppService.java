package com.music.core.service.attachment;

import com.music.integration.storege.FileStore;
import com.music.integration.storege.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultAttachmentAppService implements AttachmentAppService {

    private final FileStore fileStore;

    @Override
    public UploadFile fileUpload(MultipartFile file, String dirName) {
        return fileStore.upload(file, dirName);
    }
}
