package com.vedalvi.CashFlow.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "attachment")
@EntityListeners(AuditingEntityListener.class)
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String fileName;

    @Column(length = 2048, nullable = false)
    private String fileUrl;

    @Column(length = 100,unique = true)
    private String fileType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @CreatedDate
    @Column(name = "uploadTime", nullable = false, updatable = false)
    private Instant createdAt;
}
