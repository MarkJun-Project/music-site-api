package com.music.integration.storege;

import com.music.integration.support.RuntimeIOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.music.common.support.Preconditions.require;

@Component
public class LocalFileStore implements FileStore{
    @Override
    public List<UploadFile> uploads(List<MultipartFile> multipartFiles, String fileDir) {
        return multipartFiles.stream()
                .filter(file -> !file.isEmpty())
                .map(file -> upload(file, fileDir))
                .toList();
    }

    @Override
    public UploadFile upload(MultipartFile multipartFile, String fileDir) {
        require(!multipartFile.isEmpty());

        String originalFilename = multipartFile.getOriginalFilename();
        String uploadFilename = crateStoreFileName(originalFilename);
        String fullPath = getFullPath(fileDir, uploadFilename);

        try {
            multipartFile.transferTo(new File(fullPath));
        }catch (IOException e) {
            throw new RuntimeIOException(e);
        }

        return UploadFile.create(fullPath, uploadFilename, originalFilename);
    }

    private String crateStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + originalFilename;
    }

    private String getFullPath(String fileDir, String fileName) {
        return fileDir + File.separator + fileName;
    }
}
