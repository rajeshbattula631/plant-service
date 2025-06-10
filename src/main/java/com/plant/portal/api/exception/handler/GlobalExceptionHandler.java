package com.plant.portal.api.exception.handler;

import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.MethodArgumentNotValidException; 
import org.springframework.web.bind.annotation.ControllerAdvice; 
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.context.request.WebRequest;

import com.plant.portal.api.exception.BadRequestException;
import com.plant.portal.api.exception.ResourceNotFoundException;
import com.plant.portal.api.exception.UnauthorizedException;

import java.time.LocalDateTime; 
import java.util.LinkedHashMap; 
import java.util.Map; 
import java.util.stream.Collectors; 
 
@ControllerAdvice 
public class GlobalExceptionHandler { 
 
    @ExceptionHandler(ResourceNotFoundException.class) 
    public ResponseEntity<Object> handleResourceNotFoundException( 
            ResourceNotFoundException ex, WebRequest request) { 
        Map<String, Object> body = new LinkedHashMap<>(); 
        body.put("timestamp", LocalDateTime.now()); 
        body.put("status", HttpStatus.NOT_FOUND.value()); 
        body.put("error", "Not Found"); 
        body.put("message", ex.getMessage()); 
        body.put("path", request.getDescription(false).replace("uri=", "")); 
         
        if (ex.getResourceName() != null) { 
            body.put("resource", ex.getResourceName()); 
            body.put("field", ex.getFieldName()); 
            body.put("value", ex.getFieldValue()); 
        } 
         
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); 
    } 
 
    @ExceptionHandler(BadRequestException.class) 
    public ResponseEntity<Object> handleBadRequestException( 
            BadRequestException ex, WebRequest request) { 
        Map<String, Object> body = new LinkedHashMap<>(); 
        body.put("timestamp", LocalDateTime.now()); 
        body.put("status", HttpStatus.BAD_REQUEST.value()); 
        body.put("error", "Bad Request"); 
        body.put("message", ex.getMessage()); 
        body.put("path", request.getDescription(false).replace("uri=", "")); 
         
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); 
    } 
 
    @ExceptionHandler(UnauthorizedException.class) 
    public ResponseEntity<Object> handleUnauthorizedException( 
            UnauthorizedException ex, WebRequest request) { 
        Map<String, Object> body = new LinkedHashMap<>(); 
        body.put("timestamp", LocalDateTime.now()); 
        body.put("status", HttpStatus.UNAUTHORIZED.value()); 
        body.put("error", "Unauthorized"); 
        body.put("message", ex.getMessage()); 
        body.put("path", request.getDescription(false).replace("uri=", "")); 
         
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED); 
    } 
 
    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<Object> handleMethodArgumentNotValid( 
            MethodArgumentNotValidException ex, WebRequest request) { 
        Map<String, Object> body = new LinkedHashMap<>(); 
        body.put("timestamp", LocalDateTime.now()); 
        body.put("status", HttpStatus.BAD_REQUEST.value()); 
        body.put("error", "Validation Error"); 
         
        // Get all validation errors 
        String errors = ex.getBindingResult() 
                .getFieldErrors() 
                .stream() 
                .map(error -> error.getField() + ": " + error.getDefaultMessage()) 
                .collect(Collectors.joining(", ")); 
         
        body.put("message", errors); 
        body.put("path", request.getDescription(false).replace("uri=", "")); 
         
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); 
    } 
 
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<Object> handleAllExceptions( 
            Exception ex, WebRequest request) { 
        Map<String, Object> body = new LinkedHashMap<>(); 
        body.put("timestamp", LocalDateTime.now()); 
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value()); 
        body.put("error", "Internal Server Error"); 
        body.put("message", "An unexpected error occurred"); 
        body.put("path", request.getDescription(false).replace("uri=", "")); 
         
        // In development, you might want to include the stack trace 
        // body.put("details", ex.getMessage()); 
         
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR); 
    } 
} 
