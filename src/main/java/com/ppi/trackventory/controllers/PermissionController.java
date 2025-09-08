package com.ppi.trackventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppi.trackventory.models.Permission;
import com.ppi.trackventory.models.PermissionId;
import com.ppi.trackventory.models.Profile;
import com.ppi.trackventory.services.impl.PermissionService;

@RestController
@RequestMapping("/permissions")
@CrossOrigin("*")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // Obtener un permiso por su ID compuesto
    @GetMapping("/{profileId}/{formUrl}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long profileId, @PathVariable Integer formUrl) {
        PermissionId permissionId = new PermissionId(profileId, formUrl);
        Optional<Permission> permission = permissionService.getPermissionById(permissionId);
        return permission.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo permiso
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission newPermission = permissionService.savePermission(permission);
        return new ResponseEntity<>(newPermission, HttpStatus.CREATED);
    }

    // Eliminar un permiso por su ID compuesto
    @DeleteMapping("/{profileId}/{formUrl}")
    public ResponseEntity<HttpStatus> deletePermission(@PathVariable Long profileId, @PathVariable Integer formUrl) {
        try {
            PermissionId permissionId = new PermissionId(profileId, formUrl);
            permissionService.deletePermissionById(permissionId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener permisos por perfil
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<List<Permission>> getPermissionsByProfile(@PathVariable Long profileId) {
        Profile profile = new Profile();
        profile.setId(profileId);
        List<Permission> permissions = permissionService.getPermissionsByProfile(profile);
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }
}
