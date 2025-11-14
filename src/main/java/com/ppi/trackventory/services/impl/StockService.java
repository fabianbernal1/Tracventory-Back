package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ppi.trackventory.models.ProductVariation;
import com.ppi.trackventory.models.Stock;
import com.ppi.trackventory.models.StockId;
import com.ppi.trackventory.models.Store;
import com.ppi.trackventory.repositories.StockRepository;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductVariationService productVariationService;
    

    // Obtener todos los stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<Stock> getStockById(Long storeId, String variationCode) {
        Store store = storeService.getStoreById(storeId).orElse(null);
        ProductVariation variation = productVariationService.getVariationById(variationCode).orElse(null);

        if (store != null && variation != null) {
            StockId stockId = new StockId(store, variation);
            return stockRepository.findById(stockId);
        }
        return Optional.empty();
    }

    // Crear o actualizar un stock
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    // Eliminar un stock por ID compuesto
    public void deleteStockById(Long storeId, String variationCode) {
        Store store = storeService.getStoreById(storeId).orElse(null);
        ProductVariation variation = productVariationService.getVariationById(variationCode).orElse(null);

        if (store != null && variation != null) {
            StockId stockId = new StockId(store, variation);
            stockRepository.deleteById(stockId);
        }
    }
    
 // Obtener stocks por tienda
    public List<Stock> getStockByStoreId(Long storeId) {
        Store store = storeService.getStoreById(storeId).orElse(null);
        if (store != null) {
            return stockRepository.findByIdStore(store);
        }
        return List.of();
    }

    // Obtener stocks por variación de producto
    public List<Stock> getStockByVariationCode(String variationCode) {
        ProductVariation variation = productVariationService.getVariationById(variationCode).orElse(null);
        if (variation != null) {
            return stockRepository.findByIdVariation(variation);
        }
        return List.of();
    }
}