package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Profile;
import com.ppi.trackventory.repositories.ProfileRepository;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    // Crear o actualizar un perfil
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    // Obtener todos los perfiles
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    // Obtener un perfil por ID
    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    // Eliminar un perfil por ID
    public void deleteProfileById(Long id) {
        profileRepository.deleteById(id);
    }
}
