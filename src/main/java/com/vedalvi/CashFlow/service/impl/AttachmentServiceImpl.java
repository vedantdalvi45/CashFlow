package com.vedalvi.CashFlow.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vedalvi.CashFlow.dto.response.AttachmentResponse;
import com.vedalvi.CashFlow.exception.DuplicateEntryException;
import com.vedalvi.CashFlow.exception.NotFoundException;
import com.vedalvi.CashFlow.exception.ResourceNotFoundException;
import com.vedalvi.CashFlow.model.Attachment;
import com.vedalvi.CashFlow.model.Transaction;
import com.vedalvi.CashFlow.model.User;
import com.vedalvi.CashFlow.repository.AttachmentRepository;
import com.vedalvi.CashFlow.repository.TransactionRepository;
import com.vedalvi.CashFlow.repository.UserRepository;
import com.vedalvi.CashFlow.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private AttachmentRepository attachmentRepository;


    @Override
    public Attachment uploadAttachment(Long transactionId, MultipartFile multipartFile, String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + userEmail));

        // Security Check: Ensure the transaction exists and belongs to the current user
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, user.getId())
                .orElseThrow(() -> new NotFoundException("Transaction not found or you do not have permission to modify it."));

        // 1. Upload the physical file
        String imageUrl = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss");
            String publicId =formatter.format(transaction.getTime().atZone(ZoneId.systemDefault()))+"_"+transaction.getType() + "_" +transactionId.toString();

            Map<?, ?> uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    ObjectUtils.asMap("folder", "CashFlow/users/" + userEmail + "/transactions/",
                            "public_id", publicId,
                            "overwrite", true,
                            "format", extension));
            imageUrl = uploadResult.get("secure_url").toString();
        }catch (IOException e){

        }

        // 2. Save the metadata to the database
        Attachment attachment = Attachment.builder()
                .fileName(multipartFile.getOriginalFilename())
                .fileUrl(imageUrl)
                .fileType(multipartFile.getContentType())
                .transaction(transaction)
                .createdAt(transaction.getTime())
                .build();

        try {
            return attachmentRepository.save(attachment);
        }catch (Exception e){
            throw new DuplicateEntryException(e.getLocalizedMessage());
        }


    }



    @Override
    public List<Attachment> getAttachmentsForTransaction(Long transactionId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found with email: " + userEmail));
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, user.getId())
                .orElseThrow(() -> new NotFoundException("Transaction not found or you do not have permission to modify it."));
        List<Attachment> attachments = attachmentRepository.findByTransaction(transaction);
        if (!attachments.isEmpty()) {
            return attachments;
        }
        throw new NotFoundException("No attachments found for the given transaction.");
    }

    @Override
    public void deleteAttachment(Long attachmentId, String userEmail) throws IOException {

    }
}
