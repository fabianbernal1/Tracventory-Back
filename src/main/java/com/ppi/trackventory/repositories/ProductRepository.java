package com.ppi.trackventory.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ppi.trackventory.models.Product;
import com.ppi.trackventory.models.ProductCategory;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("SELECT p FROM Product p WHERE " +
           "(:productId IS NULL OR p.productId = :productId) AND " +
           "(:name IS NULL OR p.name = :name) AND " +
           "(:purchasePrice IS NULL OR p.purchasePrice = :purchasePrice) AND " +
           "(:salePrice IS NULL OR p.salePrice = :salePrice) AND " +
           "(:categoryId IS NULL OR p.category.id = :categoryId)")
    List<Product> findByFilters(@Param("productId") String productId,
                                @Param("name") String name,
                                @Param("purchasePrice") BigDecimal purchasePrice,
                                @Param("salePrice") BigDecimal salePrice,
                                @Param("categoryId") Long categoryId);
}
