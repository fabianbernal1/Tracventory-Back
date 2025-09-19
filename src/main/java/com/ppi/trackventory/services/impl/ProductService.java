package com.ppi.trackventory.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.configurations.BusinessException;
import com.ppi.trackventory.models.Product;
import com.ppi.trackventory.repositories.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByFilters(String productId, String name, BigDecimal purchasePrice, BigDecimal salePrice, Long categoryId) {
        return productRepository.findByFilters(productId, name, purchasePrice, salePrice, categoryId );
    }

    public Optional<Product> getProductById(String productId) {
        return productRepository.findById(productId);
    }

    public Product saveProduct(Product product) {
        BigDecimal purchasePrice = product.getPurchasePrice();
        BigDecimal salePrice = product.getSalePrice();
        Integer profitMargin = product.getProfitMargin();

        boolean hasPurchase = purchasePrice != null;
        boolean hasSale = salePrice != null;
        boolean hasMargin = profitMargin != null;

        // Caso 1: Mandaron los 3 → recalcular el margen
        if (hasPurchase && hasSale && hasMargin) {
            BigDecimal margin = salePrice.subtract(purchasePrice)
                    .divide(purchasePrice, 4, RoundingMode.HALF_UP) // 4 decimales de precisión
                    .multiply(BigDecimal.valueOf(100));
            product.setProfitMargin(margin.setScale(0, RoundingMode.HALF_UP).intValue()); // redondea a entero
        }
        // Caso 2: Mandaron purchase + sale → calcular margen
        else if (hasPurchase && hasSale) {
            BigDecimal margin = salePrice.subtract(purchasePrice)
                    .divide(purchasePrice, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            product.setProfitMargin(margin.setScale(0, RoundingMode.HALF_UP).intValue());
        }
        // Caso 3: Mandaron purchase + margin → calcular venta
        else if (hasPurchase && hasMargin) {
            salePrice = purchasePrice.multiply(
                    BigDecimal.ONE.add(BigDecimal.valueOf(profitMargin).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
            );
            product.setSalePrice(salePrice.setScale(2, RoundingMode.HALF_UP)); // 2 decimales típicos en dinero
        }
        // Caso 4: Mandaron sale + margin → calcular compra
        else if (hasSale && hasMargin) {
            purchasePrice = salePrice.divide(
                    BigDecimal.ONE.add(BigDecimal.valueOf(profitMargin).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)),
                    4,
                    RoundingMode.HALF_UP
            );
            product.setPurchasePrice(purchasePrice.setScale(2, RoundingMode.HALF_UP));
        }
        // Caso inválido
        else {
            throw new BusinessException("Debe enviar al menos dos valores entre precio de compra, precio de venta y margen.");
        }

        return productRepository.save(product);
    }



    public void deleteProductById(String productId) {
        productRepository.deleteById(productId);
    }
}
