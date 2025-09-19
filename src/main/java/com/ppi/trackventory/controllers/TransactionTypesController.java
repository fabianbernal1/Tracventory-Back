package com.ppi.trackventory.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ppi.trackventory.models.TransactionTypes;
import com.ppi.trackventory.services.impl.TransactionTypesService;

@RestController
@RequestMapping("/transactionTypes")
@CrossOrigin("*")
public class TransactionTypesController {

    @Autowired
    private TransactionTypesService transactionTypesService;

    @PreAuthorize("hasAuthority('/transactionTypes:r')")
    // Obtener todos los tipos de transacción
    @GetMapping
    public ResponseEntity<List<TransactionTypes>> getAllTransactionTypes() {
        List<TransactionTypes> types = transactionTypesService.getAllTransactionTypes();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('/transactionTypes:r')")
    // Obtener un tipo de transacción por ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionTypes> getTransactionTypeById(@PathVariable Integer id) {
        try {
            TransactionTypes type = transactionTypesService.getTransactionTypeById(id);
            return new ResponseEntity<>(type, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('/transactionTypes:c')")
    // Crear un nuevo tipo de transacción
    @PostMapping
    public ResponseEntity<TransactionTypes> createTransactionType(@RequestBody TransactionTypes transactionType) {
        try {
            TransactionTypes newType = transactionTypesService.saveTransactionType(transactionType);
            return new ResponseEntity<>(newType, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('/transactionTypes:u')")
    // Actualizar un tipo de transacción existente
    @PutMapping("/{id}")
    public ResponseEntity<TransactionTypes> updateTransactionType(@PathVariable Integer id, @RequestBody TransactionTypes updatedType) {
        try {
            TransactionTypes existingType = transactionTypesService.getTransactionTypeById(id);
            existingType.setName(updatedType.getName());
            existingType.setDescription(updatedType.getDescription());
            existingType.setEnabled(updatedType.getEnabled());
            TransactionTypes savedType = transactionTypesService.saveTransactionType(existingType);
            return new ResponseEntity<>(savedType, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('/transactionTypes:d')")
    // Eliminar un tipo de transacción por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransactionType(@PathVariable Integer id) throws Exception {
            transactionTypesService.deleteTransactionTypeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
 
}
