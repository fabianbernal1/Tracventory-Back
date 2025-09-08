package com.ppi.trackventory.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ppi.trackventory.models.ProductVariation;
import com.ppi.trackventory.models.Stock;
import com.ppi.trackventory.models.StockId;
import com.ppi.trackventory.models.Store;

public interface StockRepository extends JpaRepository<Stock, StockId> {
	// Buscar stocks por tienda
    List<Stock> findByIdStore(Store store);
    
    // Buscar stocks por variación de producto
    List<Stock> findByIdVariation(ProductVariation variation);
}
