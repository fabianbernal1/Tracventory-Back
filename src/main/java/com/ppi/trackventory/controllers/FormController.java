package com.ppi.trackventory.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ppi.trackventory.models.Form;
import com.ppi.trackventory.services.impl.FormService;

@RestController
@RequestMapping("/forms")
@CrossOrigin("*")
public class FormController {

    @Autowired
    private FormService formService;
    
    @PreAuthorize("hasAuthority('/forms:r')")
    // Obtener todos los formularios
    @GetMapping
    public ResponseEntity<List<Form>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        return new ResponseEntity<>(forms, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAuthority('/forms:r')")
    // Obtener un formulario por su URL
    @GetMapping("/{id}")
    public ResponseEntity<Form> getFormByUrl(@PathVariable Integer id) {
        Optional<Form> form = formService.getFormById(id);
        return form.map(f -> new ResponseEntity<>(f, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PreAuthorize("hasAuthority('/forms:c')")
    // Crear un nuevo formulario
    @PostMapping
    public ResponseEntity<Form> createForm(@RequestBody Form form) {
        Form newForm = formService.saveForm(form);
        return new ResponseEntity<>(newForm, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasAuthority('/forms:u')")
    // Actualizar un formulario existente
    @PutMapping("/{id}")
    public ResponseEntity<Form> updateForm(@PathVariable Integer id, @RequestBody Form updatedForm) {
        Optional<Form> formData = formService.getFormById(id);

        if (formData.isPresent()) {
            Form form = formData.get();
            form.setUrl(updatedForm.getUrl());
            form.setName(updatedForm.getName());
            form.setIcon(updatedForm.getIcon());
            formService.saveForm(form);
            return new ResponseEntity<>(form, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('/forms:d')")
    // Eliminar un formulario por su URL
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteForm(@PathVariable Integer id) {
        try {
            formService.deleteFormById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
