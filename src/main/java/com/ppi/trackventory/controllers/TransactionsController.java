package com.ppi.trackventory.controllers;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ppi.trackventory.models.TransactionDetails;
import com.ppi.trackventory.models.Transactions;
import com.ppi.trackventory.services.impl.TransactionsService;

@RestController
@RequestMapping("/transactions")
@CrossOrigin("*")
public class TransactionsController {

    @Autowired
    private TransactionsService transactionsService;

    @PreAuthorize("hasAuthority('/transactions:c')")
    // Guardar o actualizar una transacción con sus detalles
    @PostMapping("/save")
    public ResponseEntity<?> saveTransaction(
            @RequestBody List<TransactionDetails> transactionDetails,
            @RequestParam String buyerId,
            @RequestParam String sellerId,
            @RequestParam Integer transactionType,
            @RequestParam Integer transactionOrigin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime transactionDate) {
        try {
            Transactions savedTransaction = transactionsService.saveTransaction(buyerId, sellerId, transactionType,transactionOrigin, transactionDetails,transactionDate);
            return ResponseEntity.ok(savedTransaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('/transactions:r')")
    // Obtener una transacción por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        try {
            Transactions transaction = transactionsService.getTransactionById(id);
            return ResponseEntity.ok(transaction);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('/transactions:r')")
    // Obtener todos los detalles de una transacción específica por su ID
    @GetMapping("/{id}/details")
    public ResponseEntity<?> getTransactionDetailsByTransactionId(@PathVariable Long id) {
        try {
            Transactions transaction = transactionsService.getTransactionById(id);
            List<TransactionDetails> transactionDetails = transactionsService.getTransactionDetailsByTransaction(transaction);
            return ResponseEntity.ok(transactionDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('/transactions:r')")
    // Obtener todas las transacciones
    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions() {
        try {
            List<Transactions> transactions = transactionsService.getAllTransactions();
            
            if (transactions == null || transactions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al obtener las transacciones: " + e.getMessage());
        }
    }
    
    @PreAuthorize("hasAuthority('/transactions:r')")
    @GetMapping("/by-type")
    public ResponseEntity<?> getTransactionsByType(@RequestParam Integer transactionTypeId) {
        try {
            List<Transactions> transactions = transactionsService.getTransactionsByType(transactionTypeId);
            if (transactions.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('/transactions:d')")
    // Eliminar una transacción por su ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransactionById(@PathVariable Long id) {
        try {
            transactionsService.deleteTransactionById(id);
            return ResponseEntity.ok("Transacción eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
