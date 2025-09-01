package com.omni.cadastro_cnpj.exception;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CnpjNotFoundException.class)
    public ResponseEntity<String> handleCnpjNotFound(CnpjNotFoundException ex) {
        // Retorna 404 NOT FOUND e a mensagem da exception
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    @ExceptionHandler(CnpjAlreadyExistsException.class)
    public ResponseEntity<String> handleCnpjAlreadyExists(CnpjAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidEnum(InvalidFormatException ex) {
        Class<?> targetType = ex.getTargetType();
        
        // Verifica se é um enum
        if (targetType != null && targetType.isEnum()) {
            String allowedValues = Arrays.stream(targetType.getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            
            String message = String.format(
                "Valor inválido para %s. Valores aceitos: %s",
                ex.getPath().get(0).getFieldName(),
                allowedValues
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Valor inválido");
    }  
    
}
