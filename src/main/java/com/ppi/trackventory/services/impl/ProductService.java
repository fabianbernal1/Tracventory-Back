package com.ppi.trackventory.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Product;
import com.ppi.trackventory.models.ProductCategory;
import com.ppi.trackventory.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByFilters(String productId, String name, BigDecimal purchasePrice, BigDecimal salePrice, Long categoryId) {
        return productRepository.findByFilters(productId, name, purchasePrice, salePrice, categoryId );
    }

    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProductById(String productId) {
        productRepository.deleteById(productId);
    }
}
