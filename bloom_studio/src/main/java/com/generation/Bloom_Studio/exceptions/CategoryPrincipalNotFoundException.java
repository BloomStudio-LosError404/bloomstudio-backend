package com.generation.Bloom_Studio.exceptions;

public class CategoryPrincipalNotFoundException extends Throwable {
    public CategoryPrincipalNotFoundException(Long idCategoria) {
        super("Not found Category with id:" + idCategoria);
    }
}