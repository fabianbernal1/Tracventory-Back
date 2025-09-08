package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Color;
import com.ppi.trackventory.repositories.ColorRepository;

@Service
public class ColorService {

    @Autowired
    private ColorRepository colorRepository;

    // Crear o actualizar un color
    public Color saveColor(Color color) {
        return colorRepository.save(color);
    }

    // Obtener todos los colores
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    // Obtener un color por su nombre
    public Optional<Color> getColorById(Integer id) {
        return colorRepository.findById(id);
    }

    // Eliminar un color por su nombre
    public void deleteColorById(Integer id) {
        colorRepository.deleteById(id);
    }
    
}