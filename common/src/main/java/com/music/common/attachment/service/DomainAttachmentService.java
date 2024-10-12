package com.music.common.attachment.service;

import com.music.common.attachment.domain.Attachment;
import com.music.common.attachment.domain.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DomainAttachmentService implements AttachmentService{

    private final AttachmentRepository attachmentRepository;

    @Override
    public Attachment create(String fileUrl, String fileName, String originFileName) {
        val attachment = Attachment.create(fileUrl, fileName, originFileName);

        return attachmentRepository.save(attachment);
    }
}
