package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colores")
@CrossOrigin(origins = "*")
public class ColorsController {

    @Autowired
    private ColorService colorService;

    @GetMapping
    public List<Colors> getAll() {
        return colorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colors> getById(@PathVariable Long id) {
        return colorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Colors create(@RequestBody Colors colors) {
        return colorService.save(colors);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        colorService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
