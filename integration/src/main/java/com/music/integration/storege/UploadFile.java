package com.music.integration.storege;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import static com.music.common.support.Preconditions.require;

@Getter
public class UploadFile {
    private final String fileUrl;
    private final String fileName;
    private final String originFileName;

    private UploadFile(String fileUrl, String fileName, String originFileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.originFileName = originFileName;
    }

    public static UploadFile create(String fileUrl, String fileName, String originFileName) {
        require(Strings.isNotBlank(fileUrl));
        require(Strings.isNotBlank(fileName));
        require(Strings.isNotBlank(originFileName));

        return new UploadFile(fileUrl, fileName, originFileName);
    }
}
