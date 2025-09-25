package com.vedalvi.CashFlow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentResponse {
    private Long id;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Instant uploadTime;
}
