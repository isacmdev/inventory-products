package com.inventory.product.domain.ports;

import com.inventory.product.domain.entity.Product;

import java.util.List;

public interface ProductInterfacePortIn {
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product getProductBySku(String sku);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    List<Product> findBySkus(List<String> skus);
}
