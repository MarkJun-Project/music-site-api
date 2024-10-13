package com.music.core.service.board;

import com.music.integration.storege.UploadFile;
import org.springframework.web.multipart.MultipartFile;

public interface BoardAppService {
    UploadFile fileUpload(MultipartFile multipartFile);
}
