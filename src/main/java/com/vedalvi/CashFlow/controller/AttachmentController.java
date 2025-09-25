package com.vedalvi.CashFlow.controller;

import com.vedalvi.CashFlow.dto.response.AttachmentResponse;
import com.vedalvi.CashFlow.model.Attachment;
import com.vedalvi.CashFlow.security.CustomUserDetails;
import com.vedalvi.CashFlow.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/upload/{transactionId}")
    public ResponseEntity<AttachmentResponse> createAttachment(@RequestParam("file") MultipartFile multipartFile,
                                                              @PathVariable("transactionId") Long transactionId
            , @AuthenticationPrincipal CustomUserDetails currentUser) throws IOException {

        System.out.println(transactionId);

        Attachment attachment = attachmentService.uploadAttachment(transactionId, multipartFile, currentUser.getUsername());
        AttachmentResponse attachmentResponse =AttachmentResponse.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .fileUrl(attachment.getFileUrl())
                .fileType(attachment.getFileType())
                .uploadTime(attachment.getCreatedAt())
                .build();

        return ResponseEntity.ok(attachmentResponse);
    }
    @GetMapping("/{transactionId}")
    public ResponseEntity<List<AttachmentResponse>> getAttachmentsForTransaction(@PathVariable("transactionId")Long transactionId,
                                                                                @AuthenticationPrincipal CustomUserDetails currentUser) {
        List<Attachment> attachment = attachmentService.getAttachmentsForTransaction(transactionId, currentUser.getUsername());
        List<AttachmentResponse> attachmentResponses = attachment.stream()
                .map(attachment1 -> AttachmentResponse.builder()
                        .id(attachment1.getId())
                        .uploadTime(attachment1.getCreatedAt())
                        .fileUrl(attachment1.getFileUrl())
                        .fileName(attachment1.getFileName())
                        .fileType(attachment1.getFileType())
                        .build()).toList();
        return ResponseEntity.ok(attachmentResponses);
    }
}
