package com.inventory.product.infratructure.mapper;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.infrastructure.dto.ProductRequestDto;
import com.inventory.product.infrastructure.dto.ProductResponseDto;
import com.inventory.product.infrastructure.mapper.ProductMapperDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperDtoTest {

    @Test
    void testToDomain() {
        ProductRequestDto dto = ProductRequestDto.builder()
                .sku("SKU123")
                .name("Producto A")
                .description("Descripción A")
                .category("Categoría A")
                .build();

        Product product = ProductMapperDto.toDomain(dto);

        assertNull(product.getId());
        assertEquals("SKU123", product.getSku());
        assertEquals("Producto A", product.getName());
        assertEquals("Descripción A", product.getDescription());
        assertEquals("Categoría A", product.getCategory());
    }

    @Test
    void testToResponse() {
        Product product = Product.builder()
                .id(1)
                .sku("SKU123")
                .name("Producto A")
                .description("Descripción A")
                .category("Categoría A")
                .build();

        ProductResponseDto response = ProductMapperDto.toResponse(product);

        assertEquals(1L, response.getId());
        assertEquals("SKU123", response.getSku());
        assertEquals("Producto A", response.getName());
        assertEquals("Descripción A", response.getDescription());
        assertEquals("Categoría A", response.getCategory());
    }

    @Test
    void testToProductList() {
        Product product1 = Product.builder()
                .id(1)
                .sku("SKU1")
                .name("Producto 1")
                .description("Desc 1")
                .category("Cat 1")
                .build();

        Product product2 = Product.builder()
                .id(2)
                .sku("SKU2")
                .name("Producto 2")
                .description("Desc 2")
                .category("Cat 2")
                .build();

        List<ProductResponseDto> responseList = ProductMapperDto.toProductList(List.of(product1, product2));

        assertEquals(2, responseList.size());
        assertEquals(1L, responseList.get(0).getId());
        assertEquals("SKU1", responseList.get(0).getSku());
        assertEquals(2L, responseList.get(1).getId());
        assertEquals("SKU2", responseList.get(1).getSku());
    }
}