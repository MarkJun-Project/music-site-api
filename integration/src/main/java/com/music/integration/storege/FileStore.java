package com.music.integration.storege;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStore {
    UploadFile upload(MultipartFile multipartFile, String fileDir);
    List<UploadFile> uploads(List<MultipartFile> multipartFiles, String fileDir);
}
