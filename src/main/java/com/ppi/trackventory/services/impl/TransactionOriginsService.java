package com.ppi.trackventory.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.TransactionOrigins;
import com.ppi.trackventory.repositories.TransactionOriginsRepository;

@Service
public class TransactionOriginsService {

    @Autowired
    private TransactionOriginsRepository transactionOriginsRepository;

    // Guardar o actualizar un origen de transacción
    public TransactionOrigins saveTransactionOrigin(TransactionOrigins transactionOrigin) throws Exception {
        if (transactionOrigin.getName() == null || transactionOrigin.getName().isEmpty()) {
            throw new Exception("The transaction origin name cannot be empty.");
        }
        return transactionOriginsRepository.save(transactionOrigin);
    }

    // Obtener un origen de transacción por ID
    public TransactionOrigins getTransactionOriginById(Integer id) throws Exception {
        return transactionOriginsRepository.findById(id)
                .orElseThrow(() -> new Exception("Transaction origin not found with ID: " + id));
    }

    // Obtener todos los orígenes de transacción
    public List<TransactionOrigins> getAllTransactionOrigins() {
        return transactionOriginsRepository.findAll();
    }

    // Eliminar un origen de transacción por ID
    public void deleteTransactionOriginById(Integer id) throws Exception {
        TransactionOrigins transactionOrigin = getTransactionOriginById(id);
        transactionOriginsRepository.delete(transactionOrigin);
    }
}