package com.ppi.trackventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppi.trackventory.models.ProductCategory;
import com.ppi.trackventory.services.impl.ProductCategoryService;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;
    
    @PreAuthorize("hasAuthority('/categories:r')")
    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = productCategoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('/categories:r')")
    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getCategoryById(@PathVariable Long id) {
        Optional<ProductCategory> category = productCategoryService.getCategoryById(id);
        return category.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                       .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PreAuthorize("hasAuthority('/categories:c')")
    // Crear una nueva categoría
    @PostMapping
    public ResponseEntity<ProductCategory> createCategory(@RequestBody ProductCategory productCategory) {
        ProductCategory newCategory = productCategoryService.saveCategory(productCategory);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasAuthority('/categories:u')")
    // Actualizar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateCategory(@PathVariable Long id, @RequestBody ProductCategory updatedCategory) {
        Optional<ProductCategory> categoryData = productCategoryService.getCategoryById(id);

        if (categoryData.isPresent()) {
            ProductCategory category = categoryData.get();
            category.setName(updatedCategory.getName());
            category.setDescription(updatedCategory.getDescription());
            category.setEnabled(updatedCategory.getEnabled());
            productCategoryService.saveCategory(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('/categories:d')")
    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        try {
            productCategoryService.deleteCategoryById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
