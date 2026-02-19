package com.inventory.product.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponseDto {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private String category;
}
