package com.inventory.product.infrastructure.repository.repository;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.domain.ports.ProductInterfacePortOut;
import com.inventory.product.infrastructure.repository.entity.ProductData;
import com.inventory.product.infrastructure.repository.mapper.ProductEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ProductRepository implements ProductInterfacePortOut {
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(Product product){
        try{
            ProductData productData = ProductEntityMapper.toData(product);
            ProductData saved = productJpaRepository.save(productData);
            return ProductEntityMapper.toDomain(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error saving product");
        }
    }

    @Override
    public Product update(Long id, Product product) {
        try {
            ProductData existing = productJpaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Product not found by id " + id));

            // Actualizar solo los campos permitidos (excluyendo sku)
            existing.setName(product.getName());
            existing.setCategory(product.getCategory());
            existing.setDescription(product.getDescription());

            ProductData saved = productJpaRepository.save(existing);   // ← hace UPDATE
            return ProductEntityMapper.toDomain(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error updating product with id " + id, e);
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            return productJpaRepository.findAll().stream()
                    .map(ProductEntityMapper::toDomain)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving products");
        }
    }

    @Override
    public Product findById(Long id) {
        return productJpaRepository.findById(id).map(ProductEntityMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Product not found by id " + id));
    }

    @Override
    public Product findBySku(String sku) {
        return productJpaRepository.findBySku(sku).map(ProductEntityMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Product not found by sku " + sku));
    }

    @Override
    public void deleteById(Long id) {
        productJpaRepository.deleteById(id);
    }

    @Override
    public List<Product> findBySkus(List<String> skus) {
        return productJpaRepository.findBySkuIn(skus)
                .stream()
                .map(ProductEntityMapper::toDomain)
                .toList();
    }
}
