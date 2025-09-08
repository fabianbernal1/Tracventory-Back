package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Store;
import com.ppi.trackventory.repositories.StoreRepository;

@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    // Crear o actualizar una tienda
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    // Obtener todas las tiendas
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    // Obtener una tienda por su ID
    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    // Eliminar una tienda
    public void deleteStoreById(Long id) {
        storeRepository.deleteById(id);
    }
}

