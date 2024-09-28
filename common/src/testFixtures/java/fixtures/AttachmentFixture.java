package fixtures;

import com.music.common.attachment.domain.Attachment;

public class AttachmentFixture {

    public static Attachment create() {
        String fileUrl = "fileUrl";
        String fileName = "fileName";
        String originalFileName = "originalFileName";

        return Attachment.create(fileUrl, fileName, originalFileName);
    }
}
