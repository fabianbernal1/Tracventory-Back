package com.ppi.trackventory.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ppi.trackventory.models.Product;
import com.ppi.trackventory.models.ProductVariation;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, String> {
    List<ProductVariation> findByProduct(Product product);
}
