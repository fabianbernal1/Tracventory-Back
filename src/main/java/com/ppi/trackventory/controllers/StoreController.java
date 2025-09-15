package com.ppi.trackventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.ppi.trackventory.models.Store;
import com.ppi.trackventory.services.impl.StoreService;

@RestController
@RequestMapping("/stores")
@CrossOrigin("*")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PreAuthorize("hasAuthority('/stores:r')")
    // Listar todas las tiendas
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('/stores:r')")
    // Obtener una tienda por ID
    @GetMapping("/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        Optional<Store> store = storeService.getStoreById(id);
        return store.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('/stores:c')")
    // Crear una nueva tienda
    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store newStore = storeService.saveStore(store);
        return new ResponseEntity<>(newStore, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('/stores:u')")
 // Actualizar una tienda existente
    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store updatedStore) {
        Optional<Store> storeData = storeService.getStoreById(id);

        if (storeData.isPresent()) {
            Store store = storeData.get();
            store.setName(updatedStore.getName());
            store.setAddress(updatedStore.getAddress());
            store.setEnabled(updatedStore.getEnabled());
            storeService.saveStore(store);
            return new ResponseEntity<>(store, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('/stores:d')")
    // Eliminar una tienda
    @DeleteMapping("/{id}")
    public void deleteStore(@PathVariable Long id) {
            storeService.deleteStoreById(id);
    }
}
