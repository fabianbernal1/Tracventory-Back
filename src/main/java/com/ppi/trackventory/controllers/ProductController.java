package com.ppi.trackventory.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ppi.trackventory.models.Product;
import com.ppi.trackventory.services.impl.ProductService;

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @PreAuthorize("hasAuthority('/products:r')")
    // Get all products with optional filters
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(required = false) String productId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal purchasePrice,
            @RequestParam(required = false) BigDecimal salePrice,
            @RequestParam(required = false) Long categoryId) {
        try {
            return productService.getProductsByFilters(productId, name, purchasePrice, salePrice, categoryId);
        } catch (Exception e) {
            System.out.print(e);
        }
        return new ArrayList<>();
    }
    
    @PreAuthorize("hasAuthority('/products:r')")
    // Get product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable String productId) {
        return productService.getProductById(productId)
                .map(product -> ResponseEntity.ok().body(product))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasAuthority('/products:c')")
    // Create a new product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }
    
    @PreAuthorize("hasAuthority('/products:u')")
    // Update an existing product
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable String productId, @RequestBody Product updatedProduct) {
        return productService.getProductById(productId)
                .map(existingProduct -> {
                    updatedProduct.setProductId(productId); 
                    Product savedProduct = productService.saveProduct(updatedProduct);
                    return ResponseEntity.ok().body(savedProduct);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PreAuthorize("hasAuthority('/products:d')")
    // Delete a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable String productId) {
        return productService.getProductById(productId)
                .map(product -> {
                    productService.deleteProductById(productId);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
