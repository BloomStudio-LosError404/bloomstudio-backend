package com.generation.Bloom_Studio.service;


import com.generation.Bloom_Studio.exceptions.CategoryPrincipalNotFoundException;
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

    // findByIdcategoria

    public Category findByIdCategoria(Long idCategoria) throws CategoryPrincipalNotFoundException {
        return categoryRepository.findById(idCategoria)
                .orElseThrow(() -> new CategoryPrincipalNotFoundException(idCategoria));
    }





    // deleteCategory por ID

    public void deleteCategory (Long idCategoria) throws CategoryPrincipalNotFoundException {
        if(categoryRepository.existsById(idCategoria)){
            categoryRepository.deleteById(idCategoria);
        }else {
            throw new CategoryPrincipalNotFoundException(idCategoria);
        }
    }

    // updateCategory

    public Category updateCategory( Category category, Long idCategoria) throws CategoryPrincipalNotFoundException {
        return categoryRepository.findById(idCategoria)
                .map(categoryData->{
                    categoryData.setNombreCategoria(category.getNombreCategoria());
                    categoryData.setEstatus(category.getEstatus());

                    return categoryRepository.save(categoryData);

                })
                .orElseThrow(() -> new CategoryPrincipalNotFoundException(idCategoria));
    }


}

