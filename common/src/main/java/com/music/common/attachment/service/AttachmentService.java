package com.music.common.attachment.service;

import com.music.common.attachment.domain.Attachment;

public interface AttachmentService {
    Attachment create(String fileUrl, String fileName, String originFileName);
}
