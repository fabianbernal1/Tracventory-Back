package com.ppi.trackventory.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppi.trackventory.models.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}