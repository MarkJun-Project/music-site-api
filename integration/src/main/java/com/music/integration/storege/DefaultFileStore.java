package com.music.integration.storege;

import com.music.integration.support.RuntimeIOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.music.common.support.Preconditions.require;

@Component
@Profile({"dev", "prod"})
public class DefaultFileStore implements FileStore{
    @Value("${spring.file_upload.path}")
    private String filePath;

    @Override
    public List<UploadFile> uploads(List<MultipartFile> multipartFiles, String dirName) {
        return multipartFiles.stream()
                .filter(file -> !file.isEmpty())
                .map(file -> upload(file, dirName))
                .toList();
    }

    @Override
    public UploadFile upload(MultipartFile multipartFile, String dirName) {
        require(multipartFile.isEmpty());

        String originalFilename = multipartFile.getOriginalFilename();
        String uploadFilename = crateStoreFileName(originalFilename);
        String dirPath = getDirPath(dirName, uploadFilename);
        String fullPath = filePath + File.separator + dirPath;

        try {
            multipartFile.transferTo(new File(fullPath));
        }catch (IOException e) {
            throw new RuntimeIOException(e);
        }

        return UploadFile.create(dirPath, uploadFilename, originalFilename);
    }

    private String crateStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + originalFilename;
    }

    private String getDirPath(String fileDir, String fileName) {
        return fileDir + File.separator + fileName;
    }
}
