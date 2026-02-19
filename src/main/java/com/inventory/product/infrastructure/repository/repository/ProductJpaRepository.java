package com.inventory.product.infrastructure.repository.repository;

import com.inventory.product.infrastructure.repository.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductData, Long> {
    Optional<ProductData> findBySku(String sku);
    List<ProductData> findBySkuIn(List<String> skus);
}
