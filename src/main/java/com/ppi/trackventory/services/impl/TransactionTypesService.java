package com.ppi.trackventory.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.TransactionTypes;
import com.ppi.trackventory.repositories.TransactionTypesRepository;

@Service
public class TransactionTypesService {

    @Autowired
    private TransactionTypesRepository transactionTypesRepository;

    // Guardar o actualizar un tipo de transacción
    public TransactionTypes saveTransactionType(TransactionTypes transactionType) throws Exception {
        if (transactionType.getName() == null || transactionType.getName().isEmpty()) {
            throw new Exception("El nombre del tipo de transacción no puede estar vacío.");
        }
        return transactionTypesRepository.save(transactionType);
    }

    // Obtener un tipo de transacción por ID
    public TransactionTypes getTransactionTypeById(Integer id) throws Exception {
        return transactionTypesRepository.findById(id)
                .orElseThrow(() -> new Exception("Tipo de transacción no encontrado con ID: " + id));
    }

    // Obtener todos los tipos de transacción
    public List<TransactionTypes> getAllTransactionTypes() {
        return transactionTypesRepository.findAll();
    }

    // Eliminar un tipo de transacción por ID
    public void deleteTransactionTypeById(Integer id) throws Exception {
        TransactionTypes transactionType = getTransactionTypeById(id);
        transactionTypesRepository.delete(transactionType);
    }
}
