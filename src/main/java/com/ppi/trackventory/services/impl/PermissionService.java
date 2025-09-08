package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Permission;
import com.ppi.trackventory.models.PermissionId;
import com.ppi.trackventory.models.Profile;
import com.ppi.trackventory.repositories.PermissionRepository;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    // Crear o actualizar un permiso
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    // Obtener un permiso por su ID compuesto
    public Optional<Permission> getPermissionById(PermissionId id) {
        return permissionRepository.findById(id);
    }

    // Eliminar un permiso por su ID compuesto
    public void deletePermissionById(PermissionId id) {
        permissionRepository.deleteById(id);
    }
    
    public List<Permission> getPermissionsByProfile(Profile profile) {
        return permissionRepository.findByProfilePms(profile);
    }

}
