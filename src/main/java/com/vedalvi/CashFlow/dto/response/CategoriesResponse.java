package com.vedalvi.CashFlow.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriesResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private String categoryType;
}
