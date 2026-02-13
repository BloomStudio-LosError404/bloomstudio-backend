package com.generation.Bloom_Studio.service;


import com.generation.Bloom_Studio.model.Category;
import com.generation.Bloom_Studio.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
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

    // deleteCategory por ID

    public void deleteCategory (Long idCategoria){
        if(categoryRepository.existsById(idCategoria)){
            categoryRepository.deleteById(idCategoria);
        }else {
            throw new UserPrincipalNotFoundException(idCategoria);
        }
    }

    // updateCategory

    public Category updateCategory( Category category, Long idCategoria){
        return categoryRepository.findById(nombreCategoria)
                .map(Category categoryData ->{
                    categoryData.setNombreCategoria(category.getNombreCategoria());
                    categoryData.setEstatus(category.getEstatus());
                    categoryData.setFechaCreacion(category.getFechaCreacion());
                    return categoryRepository.save(categoryData);

                })
                .orElseThrow(() -> new CategoryNotFoundException(idCategoria);
    }

}

