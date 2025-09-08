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
    
    @Autowired
    private EmailService emailService;

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
        if (stock.getQuantity() <= 5) {
            try {
                emailService.sendHtmlEmail("fabian_bernal23222@elpoli.edu.co", "Prueba", "<!DOCTYPE html>\r\n"
                        + "<html lang=\"es\">\r\n"
                        + "<head>\r\n"
                        + "    <meta charset=\"UTF-8\">\r\n"
                        + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
                        + "    <title>Notificación de Stock Bajo</title>\r\n"
                        + "    <style>\r\n"
                        + "        @import url('https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap');\r\n"
                        + "        body { font-family: 'Lato', sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; color: #333; }\r\n"
                        + "        .email-container { max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }\r\n"
                        + "        h1 { color: #ff6f61; font-size: 24px; margin-bottom: 20px; }\r\n"
                        + "        .stock-warning { background-color: #ffeeba; color: #856404; padding: 10px; border-left: 6px solid #ff6f61; border-radius: 4px; font-weight: bold; }\r\n"
                        + "        .footer { text-align: center; margin-top: 30px; font-size: 12px; color: #777; }\r\n"
                        + "        .footer a { color: #ff6f61; text-decoration: none; }\r\n"
                        + "        .footer a:hover { text-decoration: underline; }\r\n"
                        + "    </style>\r\n"
                        + "</head>\r\n"
                        + "<body>\r\n"
                        + "    <div class=\"email-container\">\r\n"
                        + "        <h1>Notificación de Stock Bajo</h1>\r\n"
                        + "        <p>Hola,</p>\r\n"
                        + "        <p>Queremos informarte que el producto con referencia <strong>" + stock.getId().getVariation().getVariationId() + " (" + stock.getId().getVariation().getProduct().getProductId() + ")</strong> en la tienda <strong>" + stock.getId().getStore().getName() + "</strong> tiene un nivel de stock bajo. Solo quedan <strong>" + stock.getQuantity() + " unidades</strong>.</p>\r\n"
                        + "        <div class=\"stock-warning\">¡Atención! El stock está por debajo del límite recomendado.</div>\r\n"
                        + "        <p>Te recomendamos revisar el inventario para evitar faltantes y garantizar la disponibilidad de este producto.</p>\r\n"
                        + "        <p>Gracias por tu atención.</p>\r\n"
                        + "        <div class=\"footer\">\r\n"
                        + "            <p>Este es un mensaje automático. Si tienes alguna duda, <a href=\"mailto:soporte@trackventory.com\">contacta con soporte</a>.</p>\r\n"
                        + "            <p>&copy; 2025 Trackventory. Todos los derechos reservados.</p>\r\n"
                        + "        </div>\r\n"
                        + "    </div>\r\n"
                        + "</body>\r\n"
                        + "</html>\r\n"
                );
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
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