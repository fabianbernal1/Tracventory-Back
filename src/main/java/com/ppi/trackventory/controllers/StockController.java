package com.ppi.trackventory.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ppi.trackventory.models.Stock;
import com.ppi.trackventory.services.impl.ReportsService;
import com.ppi.trackventory.services.impl.StockService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/stocks")
@CrossOrigin("*")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private ReportsService reportsService;

    @PreAuthorize("hasAuthority('/stocks:r')")
    // Obtener todos los stocks
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @PreAuthorize("hasAuthority('/stocks:r')")
    // Obtener un stock por tienda y variación (ID compuesto)
    @GetMapping("/{storeId}/{variationCode}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long storeId, @PathVariable String variationCode) {
        Optional<Stock> stock = stockService.getStockById(storeId, variationCode);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('/stocks:c')")
    // Crear un nuevo stock
    @PostMapping
    public Stock createStock(@RequestBody Stock stock) {
        return stockService.saveStock(stock);
    }

    @PreAuthorize("hasAuthority('/stocks:u')")
    // Actualizar un stock existente
    @PutMapping("/{storeId}/{variationCode}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long storeId, @PathVariable String variationCode, @RequestBody Stock stockDetails) {
        Optional<Stock> stockOptional = stockService.getStockById(storeId, variationCode);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            stock.setQuantity(stockDetails.getQuantity());
            stock.setEnabled(stockDetails.getEnabled());
            return ResponseEntity.ok(stockService.saveStock(stock));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('/stocks:d')")
    // Eliminar un stock por tienda y variación (ID compuesto)
    @DeleteMapping("/{storeId}/{variationCode}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long storeId, @PathVariable String variationCode) {
        Optional<Stock> stock = stockService.getStockById(storeId, variationCode);
        if (stock.isPresent()) {
            stockService.deleteStockById(storeId, variationCode);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PreAuthorize("hasAuthority('/stocks:r')")
    // Obtener stocks por tienda
    @GetMapping("/store/{storeId}")
    public List<Stock> getStockByStoreId(@PathVariable Long storeId) {
        return stockService.getStockByStoreId(storeId);
    }
    
    @PreAuthorize("hasAuthority('/stocks:r')")
    // Obtener stocks por variación de producto
    @GetMapping("/variation/{variationCode}")
    public List<Stock> getStockByVariationCode(@PathVariable String variationCode) {
        return stockService.getStockByVariationCode(variationCode);
    }
    
    @PreAuthorize("hasAuthority('/stocks:r')")
    // Generar reporte en Excel
    @GetMapping("/report")
    public ResponseEntity<byte[]> generateExcelReport() throws IOException {
        List<Stock> data = stockService.getAllStocks();
        byte[] excelData = reportsService.generateReportStock(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(excelData);
    }
}