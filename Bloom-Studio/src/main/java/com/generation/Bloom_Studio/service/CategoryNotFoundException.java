package com.generation.Bloom_Studio.service;

public class CategoryNotFoundException {
    public CategoryNotFoundException(Long idCategoria) {
        super("Not found Category with id:" + idCategoria);
    }
}
