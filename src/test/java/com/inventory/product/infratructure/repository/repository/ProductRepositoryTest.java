package com.inventory.product.infratructure.repository.repository;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.infrastructure.repository.entity.ProductData;
import com.inventory.product.infrastructure.repository.repository.ProductJpaRepository;
import com.inventory.product.infrastructure.repository.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRepositoryTest {

    private ProductJpaRepository productJpaRepository;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productJpaRepository = mock(ProductJpaRepository.class);
        productRepository = new ProductRepository(productJpaRepository);
    }

    @Test
    void save_ok() {
        Product product = Product.builder()
                .sku("SKU-1")
                .name("Producto 1")
                .description("Desc 1")
                .category("Cat 1")
                .build();

        ProductData data = ProductData.builder()
                .sku("SKU-1")
                .name("Producto 1")
                .description("Desc 1")
                .category("Cat 1")
                .build();

        ProductData savedData = ProductData.builder()
                .id(1)
                .sku("SKU-1")
                .name("Producto 1")
                .description("Desc 1")
                .category("Cat 1")
                .build();

        when(productJpaRepository.save(any(ProductData.class))).thenReturn(savedData);

        Product result = productRepository.save(product);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("SKU-1", result.getSku());
        verify(productJpaRepository).save(any(ProductData.class));
    }

    @Test
    void save_error() {
        Product product = Product.builder().sku("SKU-1").build();
        when(productJpaRepository.save(any(ProductData.class))).thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productRepository.save(product));
        assertEquals("Error saving product", ex.getMessage());
    }

    @Test
    void update_ok() {
        Integer id = 1;
        ProductData existing = ProductData.builder()
                .id(id)
                .sku("SKU-OLD")
                .name("Old")
                .description("Old Desc")
                .category("Old Cat")
                .build();

        Product update = Product.builder()
                .id(id)
                .sku("SKU-NEW")
                .name("New")
                .description("New Desc")
                .category("New Cat")
                .build();

        ProductData updatedData = ProductData.builder()
                .id(id)
                .sku("SKU-NEW")
                .name("New")
                .description("New Desc")
                .category("New Cat")
                .build();

        when(productJpaRepository.findById(id)).thenReturn(Optional.of(existing));
        when(productJpaRepository.save(any(ProductData.class))).thenReturn(updatedData);

        Product result = productRepository.update(id, update);

        assertEquals("SKU-NEW", result.getSku());
        assertEquals("New", result.getName());
        verify(productJpaRepository).findById(id);
        verify(productJpaRepository).save(any(ProductData.class));
    }

    @Test
    void update_notFound() {
        Integer id = 2;
        Product update = Product.builder().sku("SKU-NEW").build();
        when(productJpaRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> productRepository.update(id, update));
        assertEquals("Error updating product with id 2", ex.getMessage());
    }

    @Test
    void findAll_ok() {
        ProductData data1 = ProductData.builder().id(1).sku("SKU-1").name("P1").description("D1").category("C1").build();
        ProductData data2 = ProductData.builder().id(2).sku("SKU-2").name("P2").description("D2").category("C2").build();

        when(productJpaRepository.findAll()).thenReturn(Arrays.asList(data1, data2));

        List<Product> result = productRepository.findAll();

        assertEquals(2, result.size());
        assertEquals("SKU-1", result.get(0).getSku());
        assertEquals("SKU-2", result.get(1).getSku());
        verify(productJpaRepository).findAll();
    }

    @Test
    void findAll_error() {
        when(productJpaRepository.findAll()).thenThrow(new RuntimeException("DB error"));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> productRepository.findAll());
        assertEquals("Error retrieving products", ex.getMessage());
    }

    @Test
    void findById_ok() {
        ProductData data = ProductData.builder()
                .id(1)
                .sku("SKU-1")
                .name("Producto 1")
                .description("Desc 1")
                .category("Cat 1")
                .build();

        when(productJpaRepository.findById(1)).thenReturn(Optional.of(data));

        Product result = productRepository.findById(1);

        assertEquals("SKU-1", result.getSku());
        verify(productJpaRepository).findById(1);
    }

    @Test
    void findById_notFound() {
        when(productJpaRepository.findById(99)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> productRepository.findById(99));
        assertEquals("Product not found by id 99", ex.getMessage());
    }

    @Test
    void deleteById_ok() {
        doNothing().when(productJpaRepository).deleteById(1);
        productRepository.deleteById(1);
        verify(productJpaRepository).deleteById(1);
    }
}