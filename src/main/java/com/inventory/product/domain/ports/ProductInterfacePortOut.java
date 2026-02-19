package com.inventory.product.domain.ports;

import com.inventory.product.domain.entity.Product;

import java.util.List;

public interface ProductInterfacePortOut {
    Product save(Product product);
    List<Product> findAll();
    Product findById(Long id);
    Product findBySku(String sku);
    Product update(Long id, Product product);
    void deleteById(Long id);
    List<Product> findBySkus(List<String> skus);

}