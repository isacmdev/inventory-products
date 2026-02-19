package com.inventory.product.infratructure.controller;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.domain.ports.ProductInterfacePortIn;
import com.inventory.product.infrastructure.controller.ProductController;
import com.inventory.product.infrastructure.dto.ProductRequestDto;
import com.inventory.product.infrastructure.dto.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductInterfacePortIn productInterfacePortIn;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productInterfacePortIn = mock(ProductInterfacePortIn.class);
        productController = new ProductController(productInterfacePortIn);
    }

    @Test
    void addProduct_ok() {
        ProductRequestDto request = ProductRequestDto.builder()
                .sku("SKU-1")
                .name("Producto 1")
                .description("Desc 1")
                .category("Cat 1")
                .build();

        Product savedProduct = Product.builder()
                .id(1)
                .sku("SKU-1")
                .name("Producto 1")
                .description("Desc 1")
                .category("Cat 1")
                .build();

        when(productInterfacePortIn.addProduct(any(Product.class))).thenReturn(savedProduct);

        ResponseEntity<ProductResponseDto> response = productController.addProduct(request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertEquals("SKU-1", response.getBody().getSku());
        verify(productInterfacePortIn).addProduct(any(Product.class));
    }

    @Test
    void updateProduct_ok() {
        Integer id = 1;
        ProductRequestDto request = ProductRequestDto.builder()
                .sku("SKU-2")
                .name("Producto 2")
                .description("Desc 2")
                .category("Cat 2")
                .build();

        Product updatedProduct = Product.builder()
                .id(id)
                .sku("SKU-2")
                .name("Producto 2")
                .description("Desc 2")
                .category("Cat 2")
                .build();

        when(productInterfacePortIn.updateProduct(eq(id), any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<ProductResponseDto> response = productController.updateProduct(id, request);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id.longValue(), response.getBody().getId());
        assertEquals("SKU-2", response.getBody().getSku());
        verify(productInterfacePortIn).updateProduct(eq(id), any(Product.class));
    }

    @Test
    void getAllProducts_ok() {
        Product product1 = Product.builder().id(1).sku("SKU-1").name("P1").description("D1").category("C1").build();
        Product product2 = Product.builder().id(2).sku("SKU-2").name("P2").description("D2").category("C2").build();

        when(productInterfacePortIn.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        ResponseEntity<List<ProductResponseDto>> response = productController.getAllProducts();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("SKU-1", response.getBody().get(0).getSku());
        verify(productInterfacePortIn).getAllProducts();
    }

    @Test
    void getProductById_ok() {
        Integer id = 1;
        Product product = Product.builder()
                .id(id)
                .sku("SKU-1")
                .name("Producto 1")
                .description("Desc 1")
                .category("Cat 1")
                .build();

        when(productInterfacePortIn.getProductById(id)).thenReturn(product);

        ResponseEntity<ProductResponseDto> response = productController.getProductById(id);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id.longValue(), response.getBody().getId());
        verify(productInterfacePortIn).getProductById(id);
    }

    @Test
    void deleteProduct_ok() {
        Integer id = 1;
        doNothing().when(productInterfacePortIn).deleteProduct(id);

        ResponseEntity<ProductResponseDto> response = productController.deleteProduct(id);

        assertEquals(204, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(productInterfacePortIn).deleteProduct(id);
    }
}