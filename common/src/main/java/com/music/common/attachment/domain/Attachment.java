package com.music.common.attachment.domain;

import com.music.common.support.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import static com.music.common.support.Preconditions.require;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment extends BaseEntity {
    @Column(name = "FILE_URL", length = 300, nullable = false)
    private String fileUrl;

    @Column(name = "FILE_NAME", length = 255, nullable = false)
    private String fileName;

    @Column(name = "ORIGIN_FILE_NAME", length = 500, nullable = false)
    private String originFileName;

    private Attachment(String fileUrl, String fileName, String originFileName) {
        this.fileUrl = fileUrl;
        this.fileName = fileName;
        this.originFileName = originFileName;
    }

    public static Attachment create(String fileUrl, String fileName, String originFileName) {
        require(Strings.isNotBlank(fileUrl));
        require(Strings.isNotBlank(fileName));
        require(Strings.isNotBlank(originFileName));

        return new Attachment(fileUrl, fileName, originFileName);
    }
}
