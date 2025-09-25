package com.vedalvi.CashFlow.service;

import com.vedalvi.CashFlow.model.Attachment;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface AttachmentService {

    Attachment uploadAttachment(Long transactionId, MultipartFile file, String userEmail) throws IOException;

    List<Attachment> getAttachmentsForTransaction(Long transactionId, String userEmail);

    void deleteAttachment(Long attachmentId, String userEmail) throws IOException;
}
