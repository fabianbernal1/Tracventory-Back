package com.ppi.trackventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ppi.trackventory.models.Profile;
import com.ppi.trackventory.services.impl.ProfileService;

@RestController
@RequestMapping("/profiles")
@CrossOrigin("*")
public class ProfileController {

    @Autowired
    private ProfileService profileService;
    
    @PreAuthorize("hasAuthority('/profiles:r')")
    // Obtener todos los perfiles
    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        List<Profile> profiles = profileService.getAllProfiles();
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('/profiles:r')")
    // Obtener un perfil por ID
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        Optional<Profile> profile = profileService.getProfileById(id);
        return profile.map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PreAuthorize("hasAuthority('/profiles:c')")
    // Crear un nuevo perfil
    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile newProfile = profileService.saveProfile(profile);
        return new ResponseEntity<>(newProfile, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasAuthority('/profiles:u')")
    // Actualizar un perfil existente
    @PutMapping("/{id}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long id, @RequestBody Profile updatedProfile) {
        Optional<Profile> profileData = profileService.getProfileById(id);

        if (profileData.isPresent()) {
            Profile profile = profileData.get();
            profile.setName(updatedProfile.getName());
            profile.setRol_prf(updatedProfile.getRol_prf());
            profile.setEnabled(updatedProfile.getEnabled());
            profileService.saveProfile(profile);
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('/profiles:d')")
    // Eliminar un perfil por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProfile(@PathVariable Long id) {
            profileService.deleteProfileById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
