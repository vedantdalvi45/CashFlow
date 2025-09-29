package com.vedalvi.CashFlow.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TagResponse {
    private Long id;
    private String name;
}
