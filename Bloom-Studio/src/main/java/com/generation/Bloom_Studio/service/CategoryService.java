package com.generation.Bloom_Studio.service;


import com.generation.Bloom_Studio.model.Category;
import com.generation.Bloom_Studio.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Para recuperar todoas las categorias
    public List<Category> getCatagories() {
        return categoryRepository.findAll();
    }

    // Para crear nuevas instancias

    public Category createCategory(Category newCategory){ return categoryRepository.save(newCategory);}


    // findByNombreCategoria

    public List<Category> findByNombreCategoria( String nombrecategoria){
        return categoryRepository.findByNombreCategoria(nombrecategoria);
    }

    // findByEstatus
    public List<Category> findByEstatus (Boolean estatus){
        return categoryRepository.findByEstatus(estatus);
    }

}
