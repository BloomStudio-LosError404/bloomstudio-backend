package com.generation.Bloom_Studio.exceptions.product.handler;

import com.generation.Bloom_Studio.controller.ProductController;
import com.generation.Bloom_Studio.exceptions.product.ProductBadRequestException;
import com.generation.Bloom_Studio.exceptions.product.ProductConflictException;
import com.generation.Bloom_Studio.exceptions.product.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice(assignableTypes = ProductController.class)
public class ProductExceptionHandler {
    @ExceptionHandler(ProductBadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(ProductBadRequestException ex, HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ProductConflictException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(ProductConflictException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ProductNotFoundException ex, HttpServletRequest req) {
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
