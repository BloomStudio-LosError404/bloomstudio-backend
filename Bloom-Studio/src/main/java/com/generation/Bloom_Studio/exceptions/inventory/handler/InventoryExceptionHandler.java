package com.generation.Bloom_Studio.exceptions.inventory.handler;

import com.generation.Bloom_Studio.controller.InventoryController;
import com.generation.Bloom_Studio.exceptions.inventory.InventoryBadRequestException;
import com.generation.Bloom_Studio.exceptions.inventory.InventoryConflictException;
import com.generation.Bloom_Studio.exceptions.inventory.InventoryNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice(assignableTypes = InventoryController.class)
public class InventoryExceptionHandler {
    @ExceptionHandler(InventoryBadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(InventoryBadRequestException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(InventoryConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(InventoryConflictException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(InventoryNotFoundException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message, String path) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message,
                "path", path
        ));
    }
}
