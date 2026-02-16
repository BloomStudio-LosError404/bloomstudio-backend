package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.model.Colors;
import com.generation.Bloom_Studio.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/colores")

public class ColorsController {

    private final ColorService colorService;

    @Autowired
    private ColorsController(ColorService colorService){ this.colorService = colorService; }

    // Get todas las tallas
    @GetMapping
    public ResponseEntity<List<Colors>> getAllColors(){ return ResponseEntity.ok(colorService.getColor()); }

    // Get tallas activas
    @GetMapping("/activas")
    public ResponseEntity<List<Colors>> getColorsActivos(){ return ResponseEntity.ok(colorService.getColortrue()); }

    // Get talla por Id
    @GetMapping("/{id}")
    public ResponseEntity<Colors> getColorById(@PathVariable Long id) {
        return ResponseEntity.ok(colorService.findById(id));
    }

    // POST crear nuevo color
    @PostMapping
    public ResponseEntity<Colors> createColor(@RequestBody Colors color){
        Colors nuevoColor = colorService.createColor(color);
        return new ResponseEntity<>(nuevoColor, HttpStatus.CREATED);
    }

    // PUT actualizar color
    @PostMapping("/{id]")
    public ResponseEntity<Colors> updateColor(@RequestBody Colors color, @PathVariable Long id){
        Colors colorActualizado = colorService.updateColor(color, id);
        return ResponseEntity.ok(colorActualizado);

    }

    // DELETE (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        colorService.deleteColor(id);
        return ResponseEntity.noContent().build();
    }


}
