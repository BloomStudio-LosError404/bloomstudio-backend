package com.generation.Bloom_Studio.exceptions;

public class TallaNotFoundException extends RuntimeException {
    public TallaNotFoundException(Long id) {
        super("No se encontró la talla con id: " + id);
    }
}
