package com.vedalvi.CashFlow.model;

import com.vedalvi.CashFlow.model.enums.PaymentPlatform;
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
@Table(name = "payment_mode", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_payment_mode", columnNames = {"user_id", "modeName"})
})
@EntityListeners(AuditingEntityListener.class)
public class PaymentMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String modeName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentPlatform paymentType;

    @Column(nullable = false)
    private double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
