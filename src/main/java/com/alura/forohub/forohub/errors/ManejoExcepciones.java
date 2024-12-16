package com.alura.forohub.forohub.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class ManejoExcepciones {



    @SuppressWarnings("rawtypes")
    @ExceptionHandler(CategoriaException.class)
    public ResponseEntity TratarCategoria(CategoriaException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.BAD_REQUEST.value());
        json.put("message", e.getMessage());
        return ResponseEntity.badRequest().body(json);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(PerfilException.class)
    public ResponseEntity TratarPerfil(PerfilException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.NOT_FOUND.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
    }

   @SuppressWarnings("rawtypes")
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity TratarAuthenticationException(AuthenticationException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.UNAUTHORIZED.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(json);
    }
   
    
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity TratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DatoErrorValidation::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity TratarError404(NotFoundException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.NOT_FOUND.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(json);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotRequestBodyException.class)
    public ResponseEntity TratarNoHayRequestBody(NotRequestBodyException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.BAD_REQUEST.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }

     @SuppressWarnings("rawtypes")
    @ExceptionHandler(NotUpdateDataException.class)
    public ResponseEntity TratarNotUpdateData(NotUpdateDataException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.BAD_REQUEST.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ClasesRelacionadasException.class)
    public ResponseEntity TratarRelacionesClases(ClasesRelacionadasException e) {
        Map<String, Object> json = new HashMap<>();
        json.put("status", HttpStatus.BAD_REQUEST.value());
        json.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(json);
    }


    @SuppressWarnings("rawtypes")
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity TratarErrorValidacion(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());

    }
    

    private record DatoErrorValidation(String campo, String error) {
        public DatoErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
