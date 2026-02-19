package com.inventory.product.application;

import com.inventory.product.domain.entity.Product;
import com.inventory.product.domain.ports.ProductInterfacePortIn;
import com.inventory.product.domain.ports.ProductInterfacePortOut;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceUseCase implements ProductInterfacePortIn {

    private final ProductInterfacePortOut productInterfacePortOut;

    @Override
    public Product addProduct(Product product) {
        return productInterfacePortOut.save(product);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames="productById", key="#id"),
            @CacheEvict(cacheNames="productList", allEntries=true)
    })
    public Product updateProduct(Long id, Product product) {

        Product existing = productInterfacePortOut.findById(id);

        existing.setName(product.getName());
        existing.setCategory(product.getCategory());
        existing.setDescription(product.getDescription());

        return productInterfacePortOut.update(id, existing);
    }

    @Override
    @Cacheable(cacheNames = "productList")
    public List<Product> getAllProducts() {
        return productInterfacePortOut.findAll();
    }

    @Override
    @Cacheable(cacheNames = "productById", key = "#id")
    public Product getProductById(Long id) {
        if(id <= 0) {
            throw new IllegalArgumentException("El id del producto no puede ser negativo");
        }
        return productInterfacePortOut.findById(id);
    }

    @Override
    @Cacheable(cacheNames = "productBySku", key = "#sku")
    public Product getProductBySku(String sku) {
        return productInterfacePortOut.findBySku(sku);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames="productById", key="#id"),
            @CacheEvict(cacheNames="productList", allEntries=true),
            @CacheEvict(cacheNames="productBySku", allEntries=true)
    })
    @Override
    public void deleteProduct(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id del producto no puede ser negativo");
        }
        Product product = productInterfacePortOut.findById(id);

        productInterfacePortOut.deleteById(product.getId());
    }

    @Override
    public List<Product> findBySkus(List<String> skus) {
        return productInterfacePortOut.findBySkus(skus);
    }
}