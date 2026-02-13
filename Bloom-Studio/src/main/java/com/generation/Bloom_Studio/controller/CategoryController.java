package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.model.Category;
import com.generation.Bloom_Studio.service.CategoryNotFoundException;
import com.generation.Bloom_Studio.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
RequestMapping("/api/v1")
public class CategoryController {
    private final CategoryService categoryService;

@Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Mapear getCategory

    @GetMapping("/categories")
    public List<Category>getCategories(){
        return categoryService.getCatagories();
    }
    // Clase para manejar los status
    @PostMapping("/new-category")
    public ResponseEntity<Category> saveUser(@RequestBody Category newCategory){
            Category categoryByCategory = categoryService.findByNombreCategoria(newCategory.getNombreCategoria()); ;
         // Evaluar si los valores de una instancia ya existen
        //Implementar dos códigos de estado (409 y 201)
        if(categoryByCategory != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            // return new ResponseEntity<>(HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(newCategory));
        }

    }

    // Mapear findById (200 y 404)
    @GetMapping("/category/{id}")
    public ResponseEntity<Category> categoryFindBy(@PathVariable Long idCategoria){
        try{
            return ResponseEntity.ok(categoryService.findById(idCategoria));
        }catch (CategoryNotFoundException e){
            // return ResponseEntity.notFound().build(); --Cualquiera de los dos metodos funciona
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);

        }
    }

    // Mapear (204 y 404)
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Category> deleteById(@PathVariable Long idCategoria){
        try{
            categoryService.deleteCategory(idCategoria);
             return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (CategoryNotFoundException e){
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }
    }

    // updateUser
    @PutMapping("/update-category/{idCategoria}")
    public ResponseEntity<Category> updateUser(@RequestBody Category category,@PathVariable Long idCategoria) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(CategoryService.updateCategory(category, idCategoria));

        } catch (CategoryNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
