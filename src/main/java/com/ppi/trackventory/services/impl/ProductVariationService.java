package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Product;
import com.ppi.trackventory.models.ProductVariation;
import com.ppi.trackventory.repositories.ProductVariationRepository;

@Service
public class ProductVariationService {

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private ProductService productService;

    // Get all product variations
    public List<ProductVariation> getAllProductVariations() {
        return productVariationRepository.findAll();
    }

    // Get product variation by ID
    public Optional<ProductVariation> getProductVariationById(String variationId) {
        return productVariationRepository.findById(variationId);
    }

    // Get all variations of a specific product
    public List<ProductVariation> getVariationsByProductId(String productId) throws Exception {
        Optional<Product> optionalProduct = productService.getProductById(productId);
        if (optionalProduct.isPresent()) {
            return productVariationRepository.findByProduct(optionalProduct.get());
        } else {
            throw new Exception("Product not found with ID: " + productId);
        }
    }

    // Create a new product variation
    public ProductVariation createProductVariation(ProductVariation productVariation) {
        return productVariationRepository.save(productVariation);
    }

    // Update an existing product variation
    public Optional<ProductVariation> updateProductVariation(String variationId, ProductVariation productVariationDetails) {
        return productVariationRepository.findById(variationId).map(productVariation -> {
            productVariation.setProduct(productVariationDetails.getProduct());
            productVariation.setColor(productVariationDetails.getColor());
            productVariation.setEnabled(productVariationDetails.getEnabled());
            return productVariationRepository.save(productVariation);
        });
    }

    // Delete a product variation
    public boolean deleteProductVariation(String variationId) {
        return productVariationRepository.findById(variationId).map(productVariation -> {
            productVariationRepository.delete(productVariation);
            return true;
        }).orElse(false);
    }

    // Helper for StockService
    public Optional<ProductVariation> getVariationById(String variationId) {
        return productVariationRepository.findById(variationId);
    }
}
