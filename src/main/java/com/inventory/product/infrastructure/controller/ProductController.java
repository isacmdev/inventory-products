package com.inventory.product.infrastructure.controller;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.domain.ports.ProductInterfacePortIn;
import com.inventory.product.infrastructure.dto.ProductRequestDto;
import com.inventory.product.infrastructure.dto.ProductResponseDto;
import com.inventory.product.infrastructure.mapper.ProductMapperDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductInterfacePortIn productInterfacePortIn;

    public ProductController(ProductInterfacePortIn productInterfacePortIn) {
        this.productInterfacePortIn = productInterfacePortIn;
    }

    @PostMapping
        public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product transformProductToDomain = ProductMapperDto.toDomain(productRequestDto);

        Product save = productInterfacePortIn.addProduct(transformProductToDomain);

        ProductResponseDto savedProduct = ProductMapperDto.toResponse(save);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDto productRequestDto
    ) {
        Product transformProductToDomain = ProductMapperDto.toDomain(productRequestDto);
        Product updatedProduct = productInterfacePortIn.updateProduct(id, transformProductToDomain);

        ProductResponseDto responseDto = ProductMapperDto.toResponse(updatedProduct);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productInterfacePortIn.getAllProducts();
        List<ProductResponseDto> response = products.stream()
                .map(ProductMapperDto::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/boom")
    public ResponseEntity<Void> boom() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        Product product = productInterfacePortIn.getProductById(id);
        ProductResponseDto responseDto = ProductMapperDto.toResponse(product);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponseDto> getProductBySku(@PathVariable String sku) {
        Product product = productInterfacePortIn.getProductBySku(sku);
        ProductResponseDto exists = ProductMapperDto.toResponse(product);
        return ResponseEntity.ok(exists);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable Long id) {
        productInterfacePortIn.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProductResponseDto>> findBatch(@RequestBody List<String> skus) {
        List<Product> products = productInterfacePortIn.findBySkus(skus);
        List<ProductResponseDto> response = products.stream()
                .map(ProductMapperDto::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}