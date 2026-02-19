package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.model.Etiqueta;
import com.generation.Bloom_Studio.service.EtiquetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/etiquetas")
@CrossOrigin(origins = "*")
public class EtiquetaController {

    @Autowired
    private EtiquetaService etiquetaService;

    @GetMapping
    public List<Etiqueta> getAll() {
        return etiquetaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Etiqueta> getById(@PathVariable Long id) {
        return etiquetaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Etiqueta create(@RequestBody Etiqueta etiqueta) {
        return etiquetaService.save(etiqueta);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etiquetaService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
