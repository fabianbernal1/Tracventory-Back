package com.ppi.trackventory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ppi.trackventory.models.TransactionOrigins;
import com.ppi.trackventory.services.impl.TransactionOriginsService;

@RestController
@RequestMapping("/transactionOrigins")
@CrossOrigin("*")
public class TransactionOriginsController {

    @Autowired
    private TransactionOriginsService transactionOriginsService;

    @PreAuthorize("hasAuthority('/transactionOrigins:r')")
    // Obtener todos los orígenes de transacción
    @GetMapping
    public ResponseEntity<List<TransactionOrigins>> getAllTransactionOrigins() {
        List<TransactionOrigins> origins = transactionOriginsService.getAllTransactionOrigins();
        return new ResponseEntity<>(origins, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('/transactionOrigins:r')")
    // Obtener un origen de transacción por ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionOrigins> getTransactionOriginById(@PathVariable Integer id) {
        try {
            TransactionOrigins origin = transactionOriginsService.getTransactionOriginById(id);
            return new ResponseEntity<>(origin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('/transactionOrigins:c')")
    // Crear un nuevo origen de transacción
    @PostMapping
    public ResponseEntity<TransactionOrigins> createTransactionOrigin(@RequestBody TransactionOrigins transactionOrigin) {
        try {
            TransactionOrigins newOrigin = transactionOriginsService.saveTransactionOrigin(transactionOrigin);
            return new ResponseEntity<>(newOrigin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('/transactionOrigins:u')")
    // Actualizar un origen de transacción existente
    @PutMapping("/{id}")
    public ResponseEntity<TransactionOrigins> updateTransactionOrigin(
            @PathVariable Integer id,
            @RequestBody TransactionOrigins updatedOrigin) {
        try {
            TransactionOrigins existingOrigin = transactionOriginsService.getTransactionOriginById(id);
            existingOrigin.setName(updatedOrigin.getName());
            existingOrigin.setDescription(updatedOrigin.getDescription());
            existingOrigin.setEnabled(updatedOrigin.getEnabled());
            
            TransactionOrigins savedOrigin = transactionOriginsService.saveTransactionOrigin(existingOrigin);
            return new ResponseEntity<>(savedOrigin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('/transactionOrigins:d')")
    // Eliminar un origen de transacción por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTransactionOrigin(@PathVariable Integer id) throws Exception {
            transactionOriginsService.deleteTransactionOriginById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}