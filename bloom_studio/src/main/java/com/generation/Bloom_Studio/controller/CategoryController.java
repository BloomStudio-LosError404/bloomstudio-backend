package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.exceptions.CategoryPrincipalNotFoundException;
import com.generation.Bloom_Studio.model.Category;
import com.generation.Bloom_Studio.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
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
        List<Category> existing = categoryService.findByNombreCategoria(newCategory.getNombreCategoria()); ;
        // Evaluar si los valores de una instancia ya existen
        //Implementar dos cÃ³digos de estado (409 y 201)
        if(!existing.isEmpty()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            // return new ResponseEntity<>(HttpStatus.CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(newCategory));
        }

    }

    // Mapear findById (200 y 404)
    @GetMapping("/category/{idCategoria}")
    public ResponseEntity<Category> categoryFindBy(@PathVariable Long idCategoria){
        try{
            return ResponseEntity.ok(categoryService.findByIdCategoria(idCategoria));
        }catch (CategoryPrincipalNotFoundException e){
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);

        }
    }

    // Mapear (204 y 404)
    @DeleteMapping("/delete-category/{idCategoria}")
    public ResponseEntity<Category> deleteById(@PathVariable Long idCategoria){
        try{
            categoryService.deleteCategory(idCategoria);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (CategoryPrincipalNotFoundException e){
            return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        }
    }

    // updateUser
    @PutMapping("/update-category/{idCategoria}")
    public ResponseEntity<Category> updateUser(@RequestBody Category category,@PathVariable Long idCategoria) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.updateCategory(category, idCategoria));

        } catch (CategoryPrincipalNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/categories/activas")
    public List<Category> getCategoriasActivas() {
        return categoryService.getCatagories()
                .stream()
                .filter(Category::getEstatus)
                .toList();
    }
}