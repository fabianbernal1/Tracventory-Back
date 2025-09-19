package com.ppi.trackventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ppi.trackventory.models.Rol;
import com.ppi.trackventory.services.impl.RolService;

@RestController
@RequestMapping("/roles")
@CrossOrigin("*")
public class RolController {

    @Autowired
    private RolService rolService;
    
    @PreAuthorize("hasAuthority('/roles:r')")
    // Obtener todos los roles
    @GetMapping
    public ResponseEntity<List<Rol>> getAllRoles() {
        List<Rol> roles = rolService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('/roles:r')")
    // Obtener un rol por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        Optional<Rol> rol = rolService.getRolById(id);
        return rol.map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                  .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PreAuthorize("hasAuthority('/roles:c')")
    // Crear un nuevo rol
    @PostMapping
    public ResponseEntity<Rol> createRol(@RequestBody Rol rol) {
        Rol newRol = rolService.saveRol(rol);
        return new ResponseEntity<>(newRol, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('/roles:u')")
    // Actualizar un rol existente
    @PutMapping("/{id}")
    public ResponseEntity<Rol> updateRol(@PathVariable Long id, @RequestBody Rol updatedRol) {
        Optional<Rol> rolData = rolService.getRolById(id);

        if (rolData.isPresent()) {
            Rol rol = rolData.get();
            rol.setName(updatedRol.getName());
            rol.setEnabled(updatedRol.getEnabled());
            rolService.saveRol(rol);
            return new ResponseEntity<>(rol, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('/roles:d')")
    // Eliminar un rol por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRol(@PathVariable Long id) {
            rolService.deleteRolById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
