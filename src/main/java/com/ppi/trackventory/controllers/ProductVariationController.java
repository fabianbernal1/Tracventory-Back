package com.ppi.trackventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ppi.trackventory.models.ProductVariation;
import com.ppi.trackventory.services.impl.ProductVariationService;

@RestController
@RequestMapping("/product-variations")
@CrossOrigin("*")
public class ProductVariationController {

    @Autowired
    private ProductVariationService productVariationService;
    
    @PreAuthorize("hasAuthority('/product-variations:r')")
    // Get all product variations
    @GetMapping
    public List<ProductVariation> getAllProductVariations() {
        return productVariationService.getAllProductVariations();
    }
    
    @PreAuthorize("hasAuthority('/product-variations:r')")
    // Get a product variation by ID
    @GetMapping("/{variationId}")
    public ResponseEntity<ProductVariation> getProductVariationById(@PathVariable String variationId) {
        Optional<ProductVariation> productVariation = productVariationService.getProductVariationById(variationId);
        return productVariation.map(ResponseEntity::ok)
                               .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasAuthority('/product-variations:r')")
    // Get all variations of a product
    @GetMapping("/product/{productId}")
    public List<ProductVariation> getVariationsByProductId(@PathVariable String productId) throws Exception {
        return productVariationService.getVariationsByProductId(productId);
    }
    
    @PreAuthorize("hasAuthority('/product-variations:c')")
    // Create a new product variation
    @PostMapping
    public ProductVariation createProductVariation(@RequestBody ProductVariation productVariation) {
        return productVariationService.createProductVariation(productVariation);
    }
    
    @PreAuthorize("hasAuthority('/product-variations:u')")
    // Update a product variation
    @PutMapping("/{variationId}")
    public ResponseEntity<ProductVariation> updateProductVariation(
            @PathVariable String variationId,
            @RequestBody ProductVariation productVariationDetails) {

        Optional<ProductVariation> updatedProductVariation =
                productVariationService.updateProductVariation(variationId, productVariationDetails);

        return updatedProductVariation.map(ResponseEntity::ok)
                                      .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasAuthority('/product-variations:d')")
    // Delete a product variation
    @DeleteMapping("/{variationId}")
    public ResponseEntity<Void> deleteProductVariation(@PathVariable String variationId) {
        boolean isDeleted = productVariationService.deleteProductVariation(variationId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
