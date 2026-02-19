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
    private final CacheManager cacheManager;

    @Override
    public Product addProduct(Product product) {
        return productInterfacePortOut.save(product);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "productById", key = "#id")
    public Product updateProduct(Long id, Product product) {
        Product existing = productInterfacePortOut.findById(id);

        existing.setName(product.getName());
        existing.setCategory(product.getCategory());
        existing.setDescription(product.getDescription());

        return productInterfacePortOut.update(id, existing);
    }

    @Override
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


    @Override
    @CacheEvict(cacheNames = "productById", key = "#id")
    public void deleteProduct(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El id del producto no puede ser negativo");
        }
        Product product = productInterfacePortOut.findById(id);
        String sku = product.getSku();

        productInterfacePortOut.deleteById(id);
        cacheManager.getCache("productBySku").evict(sku);
    }

    @Override
    public List<Product> findBySkus(List<String> skus) {
        return productInterfacePortOut.findBySkus(skus);
    }
}