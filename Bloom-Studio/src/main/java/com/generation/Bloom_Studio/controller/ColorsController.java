package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.model.Colors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colores")
@CrossOrigin(origins = "*")
public class ColorsController {

    @Autowired
    private ColorsService colorService;

    @GetMapping
    public List<Colors> getAll() {
        return etiquetaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colors> getById(@PathVariable Long id) {
        return etiquetaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Colors create(@RequestBody Colors colors) {
        return etiquetaService.save(colors);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etiquetaService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
