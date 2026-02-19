package com.inventory.product.infrastructure.repository.mapper;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.infrastructure.repository.entity.ProductData;

public class ProductEntityMapper {
    private ProductEntityMapper() {}

    public static Product toDomain (ProductData productData) {
        return Product.builder()
                .id(productData.getId())
                .sku(productData.getSku())
                .name(productData.getName())
                .description(productData.getDescription())
                .category(productData.getCategory())
                .build();
    }

    public static ProductData toData (Product product) {
        return ProductData.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .build();
    }
}