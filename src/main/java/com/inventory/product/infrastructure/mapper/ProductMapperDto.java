package com.inventory.product.infrastructure.mapper;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.infrastructure.dto.ProductRequestDto;
import com.inventory.product.infrastructure.dto.ProductResponseDto;

import java.util.List;

public class ProductMapperDto {
    private ProductMapperDto () {}

    public static Product toDomain (ProductRequestDto productRequestDto) {
        return Product.builder()
                .sku(productRequestDto.getSku())
                .name(productRequestDto.getName())
                .description(productRequestDto.getDescription())
                .category(productRequestDto.getCategory())
                .build();
    }

    public static ProductResponseDto toResponse (Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .build();
    }

    public static List<ProductResponseDto> toProductList (List<Product> products) {
        return products.stream()
                .map(product -> ProductResponseDto.builder()
                        .id(product.getId())
                        .sku(product.getSku())
                        .name(product.getName())
                        .description(product.getDescription())
                        .category(product.getCategory())
                        .build())
                .toList();
    }
}
