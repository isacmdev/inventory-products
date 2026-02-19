package com.inventory.product.application;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.domain.ports.ProductInterfacePortOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceUseCaseTest {

    private ProductInterfacePortOut productInterfacePortOut;
    private ProductServiceUseCase productServiceUseCase;

    @BeforeEach
    void setUp() {
        productInterfacePortOut = mock(ProductInterfacePortOut.class);
        productServiceUseCase = new ProductServiceUseCase(productInterfacePortOut);
    }

    @Test
    void addProduct_ok() {
        Product product = Product.builder().sku("SKU-1").name("P1").description("D1").category("C1").build();
        Product saved = Product.builder().id(1).sku("SKU-1").name("P1").description("D1").category("C1").build();

        when(productInterfacePortOut.save(product)).thenReturn(saved);

        Product result = productServiceUseCase.addProduct(product);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(productInterfacePortOut).save(product);
    }

    @Test
    void updateProduct_ok() {
        Integer id = 1;
        Product existing = Product.builder().id(id).sku("SKU-OLD").name("Old").description("Old").category("Old").build();
        Product update = Product.builder().sku("SKU-NEW").name("New").description("New").category("New").build();
        Product updated = Product.builder().id(id).sku("SKU-NEW").name("New").description("New").category("New").build();

        when(productInterfacePortOut.findById(id)).thenReturn(existing);
        when(productInterfacePortOut.update(eq(id), any(Product.class))).thenReturn(updated);

        Product result = productServiceUseCase.updateProduct(id, update);

        assertEquals("SKU-NEW", result.getSku());
        assertEquals("New", result.getName());
        verify(productInterfacePortOut).findById(id);
        verify(productInterfacePortOut).update(eq(id), any(Product.class));
    }

    @Test
    void getAllProducts_ok() {
        Product p1 = Product.builder().id(1).sku("SKU-1").name("P1").description("D1").category("C1").build();
        Product p2 = Product.builder().id(2).sku("SKU-2").name("P2").description("D2").category("C2").build();

        when(productInterfacePortOut.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> result = productServiceUseCase.getAllProducts();

        assertEquals(2, result.size());
        verify(productInterfacePortOut).findAll();
    }

    @Test
    void getProductById_ok() {
        Integer id = 1;
        Product product = Product.builder().id(id).sku("SKU-1").name("P1").description("D1").category("C1").build();

        when(productInterfacePortOut.findById(id)).thenReturn(product);

        Product result = productServiceUseCase.getProductById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(productInterfacePortOut).findById(id);
    }

    @Test
    void getProductById_negativeId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> productServiceUseCase.getProductById(-1));
        assertEquals("El id del producto no puede ser negativo", ex.getMessage());
        verify(productInterfacePortOut, never()).findById(anyInt());
    }

    @Test
    void deleteProduct_ok() {
        Integer id = 1;
        Product product = Product.builder().id(id).build();

        when(productInterfacePortOut.findById(id)).thenReturn(product);
        doNothing().when(productInterfacePortOut).deleteById(id);

        productServiceUseCase.deleteProduct(id);

        verify(productInterfacePortOut).findById(id);
        verify(productInterfacePortOut).deleteById(id);
    }

    @Test
    void deleteProduct_negativeId() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> productServiceUseCase.deleteProduct(-5));
        assertEquals("El id del producto no puede ser negativo", ex.getMessage());
        verify(productInterfacePortOut, never()).findById(anyInt());
        verify(productInterfacePortOut, never()).deleteById(anyInt());
    }
}