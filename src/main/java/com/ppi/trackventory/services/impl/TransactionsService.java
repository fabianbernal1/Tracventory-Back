package com.ppi.trackventory.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.configurations.BusinessException;
import com.ppi.trackventory.models.TransactionDetails;
import com.ppi.trackventory.models.TransactionTypes;
import com.ppi.trackventory.models.Transactions;
import com.ppi.trackventory.repositories.TransactionDetailsRepository;
import com.ppi.trackventory.repositories.TransactionsRepository;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private TransactionDetailsRepository transactionDetailRepository;
    
    @Autowired
    private TransactionTypesService transactionTypesService;
    
    @Autowired
    private TransactionOriginsService transactionOriginsService;
    
    @Autowired
    private UserServiceImpl userService;
    
    @Autowired
    private StockService stockService;
    
    // Guardar o actualizar una transacción con sus detalles
    public Transactions saveTransaction(String buyer, String seller, Integer transactionType,Integer transactionOrigin, List<TransactionDetails> transactionDetails,LocalDateTime transactionDate, Boolean enabled ) throws Exception {
        if (buyer == null || seller == null || transactionType == 0 || transactionDetails == null || transactionDetails.isEmpty()) {
            throw new BusinessException("Faltan datos obligatorios para registrar la transacción.");
        }
     // Obtener el tipo de transacción una sola vez
        TransactionTypes type = transactionTypesService.getTransactionTypeById(transactionType);
        if (type == null) {
            throw new BusinessException("El tipo de transacción es inválido.");
        }

        // 🔍 VALIDACIÓN ANTES DE GUARDAR
        if (type.getId() == 1 || type.getId() == 3) { // 1: Venta, 3: Devolución a proveedor
            for (TransactionDetails detail : transactionDetails) {
                int stockActual = detail.getStock().getQuantity();
                int solicitado = detail.getQuantity();

                if (solicitado > stockActual) {
                    throw new BusinessException(
                        "Stock insuficiente para el producto '" 
                        + detail.getStock().getId().getVariation().getProduct().getName() + 
                        "'. Disponible: " + stockActual + ", Solicitado: " + solicitado
                    );
                }
            }
        }

        // Crear nueva transacción
        Transactions transaction = new Transactions();
        transaction.setBuyer(userService.getUserById(buyer).orElse(null));
        transaction.setSeller(userService.getUserById(seller).orElse(null));
        transaction.setTransactionType(transactionTypesService.getTransactionTypeById(transactionType));
        transaction.setTransactionOrigin(transactionOriginsService.getTransactionOriginById(transactionOrigin));
        transaction.setDate(Date.from(transactionDate.atZone(ZoneId.systemDefault()).toInstant())); 
        transaction.setEnabled(enabled);
        // Guardar la transacción
        Transactions savedTransaction = transactionsRepository.save(transaction);

        // Guardar los detalles de la transacción
        if (savedTransaction != null) {
            for (TransactionDetails detail : transactionDetails) {
                detail.setTransaction(savedTransaction);
                detail.setId(null); 
                
                detail = transactionDetailRepository.save(detail);
                
                if (detail != null) {
                	switch ( transaction.getTransactionType().getId()) {
                    case 1:
                    	detail.getStock().setQuantity(detail.getStock().getQuantity()-detail.getQuantity());
                    	stockService.saveStock(detail.getStock());
                        break;
                    case 2:
                    	detail.getStock().setQuantity(detail.getStock().getQuantity()+detail.getQuantity());
                    	stockService.saveStock(detail.getStock());
                        break;
                    case 3:
                    	detail.getStock().setQuantity(detail.getStock().getQuantity()-detail.getQuantity());
                    	stockService.saveStock(detail.getStock());
                        break;
                    case 4:
                    	detail.getStock().setQuantity(detail.getStock().getQuantity()+detail.getQuantity());
                    	stockService.saveStock(detail.getStock());
                        break;
                }
                }
            }
        }

        return savedTransaction;
    }

    // Obtener una transacción por ID
    public Transactions getTransactionById(Long id) throws Exception {
        return transactionsRepository.findById(id)
                .orElseThrow(() -> new Exception("Transacción no encontrada con el ID: " + id));
    }
    // Obtener transacciones por tipo
    public List<Transactions> getTransactionsByType(Integer transactionTypeId) throws Exception {
    	TransactionTypes type = transactionTypesService.getTransactionTypeById(transactionTypeId);
    	if (type == null) {
            throw new BusinessException("El tipo de transacción no puede ser nulo.");
        }
        return transactionsRepository.findByTransactionType(type);
    }

    // Obtener los detalles de una transacción específica
    public List<TransactionDetails> getTransactionDetailsByTransaction(Transactions transaction) {
        return transactionDetailRepository.findByTransaction(transaction);
    }

    // Obtener todas las transacciones
    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    // Eliminar una transacción por ID (y también sus detalles)
    public void deleteTransactionById(Long id) throws Exception {
        Transactions transaction = getTransactionById(id);
        List<TransactionDetails> details = getTransactionDetailsByTransaction(transaction);

        // Eliminar los detalles de la transacción antes de eliminar la transacción
        if (!details.isEmpty()) {
            transactionDetailRepository.deleteAll(details);
        }
        
        transactionsRepository.delete(transaction);
    }
}
