package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.ProductCategory;
import com.ppi.trackventory.repositories.ProductCategoryRepository;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    // Crear o actualizar una categoría de producto
    public ProductCategory saveCategory(ProductCategory category) {
        return productCategoryRepository.save(category);
    }

    // Obtener todas las categorías de productos
    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    // Obtener una categoría de producto por ID
    public Optional<ProductCategory> getCategoryById(Long id) {
        return productCategoryRepository.findById(id);
    }

    // Eliminar una categoría de producto
    public void deleteCategoryById(Long id) {
        productCategoryRepository.deleteById(id);
    }
}

