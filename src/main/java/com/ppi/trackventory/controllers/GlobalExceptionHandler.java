package com.ppi.trackventory.controllers;

import java.nio.file.AccessDeniedException;

import javax.naming.AuthenticationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ppi.trackventory.configurations.BusinessException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Maneja las violaciones de integridad referencial (FK, unique, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("No se puede eliminar el registro porque está asociado a otras entidades.");
    }
    
    // 🔐 Usuario autenticado pero sin permiso
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("Permiso denegado. No tienes acceso a este recurso.");
    }

    // 🔒 Usuario no autenticado (no logueado)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthentication(AuthenticationException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Debe iniciar sesión para acceder.");
    }

    // Puedes agregar más handlers para otras excepciones
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocurrió un error inesperado. Contacte al administrador. " + ex.getMessage());
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

}