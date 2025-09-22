package com.vedalvi.CashFlow.repository;


import com.vedalvi.CashFlow.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findByTransactionId(Long transactionId);
}
