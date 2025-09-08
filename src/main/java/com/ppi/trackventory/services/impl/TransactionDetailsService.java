package com.ppi.trackventory.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Stock;
import com.ppi.trackventory.models.TransactionDetails;
import com.ppi.trackventory.models.Transactions;
import com.ppi.trackventory.repositories.TransactionDetailsRepository;

@Service
public class TransactionDetailsService {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Autowired
    private StockService stockService;

    // Guardar detalles de una transacción
    public List<TransactionDetails> saveTransactionDetails(List<TransactionDetails> details) throws Exception {
        if (details == null || details.isEmpty()) {
            throw new Exception("No se pueden guardar detalles vacíos.");
        }

        for (TransactionDetails detail : details) {
            if (detail.getStock().getQuantity() < detail.getQuantity()) {
                throw new Exception("Stock insuficiente para completar la transacción.");
            }
        }

        // Reducir el stock y guardar los detalles
        details.forEach(detail -> {
            Stock stock = detail.getStock();
            stock.setQuantity(stock.getQuantity() - detail.getQuantity());
            stockService.saveStock(stock);
        });

        return transactionDetailsRepository.saveAll(details);
    }

    // Obtener detalles por transacción
    public List<TransactionDetails> getDetailsByTransaction(Transactions transaction) throws Exception {
        if (transaction == null) {
            throw new Exception("La transacción no puede ser nula.");
        }
        return transactionDetailsRepository.findByTransaction(transaction);
    }

    // Eliminar detalles de una transacción
    public void deleteDetailsByTransaction(Transactions transaction) throws Exception {
        List<TransactionDetails> details = getDetailsByTransaction(transaction);
        transactionDetailsRepository.deleteAll(details);
    }
}
