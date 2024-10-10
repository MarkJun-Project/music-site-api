package com.music.core.service.attachment;

import com.music.integration.storege.UploadFile;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentAppService {
    UploadFile fileUpload(MultipartFile file, String dirName);
}
