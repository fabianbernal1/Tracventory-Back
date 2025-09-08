package com.ppi.trackventory.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ppi.trackventory.models.Form;
import com.ppi.trackventory.repositories.FormRepository;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    // Crear o actualizar un formulario
    public Form saveForm(Form form) {
        return formRepository.save(form);
    }

    // Obtener todos los formularios
    public List<Form> getAllForms() {
        return formRepository.findAll();
    }

    // Obtener un formulario por URL
    public Optional<Form> getFormById(Integer id) {
        return formRepository.findById(id);
    }

    // Eliminar un formulario por URL
    public void deleteFormById(Integer id) {
        formRepository.deleteById(id);
    }
}
