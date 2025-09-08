package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Rol;
import com.ppi.trackventory.repositories.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    // Crear o actualizar un rol
    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    // Obtener todos los roles
    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    // Obtener un rol por ID
    public Optional<Rol> getRolById(Long id) {
        return rolRepository.findById(id);
    }

    // Eliminar un rol por ID
    public void deleteRolById(Long id) {
        rolRepository.deleteById(id);
    }
}
