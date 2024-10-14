package com.music.integration.storege;

import lombok.Getter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@Profile("test")
public class MockFileStore implements FileStore{

    @Override
    public UploadFile upload(MultipartFile multipartFile, String fileDir) {
        return UploadFile.create(fileDir + File.separator + multipartFile.getOriginalFilename(), "randomName.mp4", multipartFile.getOriginalFilename());
    }

    @Override
    public List<UploadFile> uploads(List<MultipartFile> multipartFiles, String fileDir) {
        List<UploadFile> result = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            result.add(upload(file, fileDir));
        }
        return result;
    }

}
