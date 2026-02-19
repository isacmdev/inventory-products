package com.inventory.product.infratructure.repository.mapper;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.infrastructure.repository.entity.ProductData;
import com.inventory.product.infrastructure.repository.mapper.ProductEntityMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityMapperTest {

    @Test
    void testToDomain() {
        ProductData data = ProductData.builder()
                .id(1)
                .sku("SKU-001")
                .name("Producto Test")
                .description("Descripción Test")
                .category("Categoría Test")
                .build();

        Product product = ProductEntityMapper.toDomain(data);

        assertEquals(1, product.getId());
        assertEquals("SKU-001", product.getSku());
        assertEquals("Producto Test", product.getName());
        assertEquals("Descripción Test", product.getDescription());
        assertEquals("Categoría Test", product.getCategory());
    }

    @Test
    void testToData() {
        Product product = Product.builder()
                .id(2)
                .sku("SKU-002")
                .name("Producto Data")
                .description("Descripción Data")
                .category("Categoría Data")
                .build();

        ProductData data = ProductEntityMapper.toData(product);

        assertEquals(2, data.getId());
        assertEquals("SKU-002", data.getSku());
        assertEquals("Producto Data", data.getName());
        assertEquals("Descripción Data", data.getDescription());
        assertEquals("Categoría Data", data.getCategory());
    }
}