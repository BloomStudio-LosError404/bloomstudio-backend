package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.model.Talla;
import com.generation.Bloom_Studio.service.TallaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tallas")
@CrossOrigin(origins = "*")
public class TallaController {
    private final TallaService tallaService;

    @Autowired
    public TallaController(TallaService tallaService) {
        this.tallaService = tallaService;
    }

    //  GET todas las tallas
    @GetMapping("/AllTallas")
    public ResponseEntity<List<Talla>> getAllTallas() {
        return ResponseEntity.ok(tallaService.getTalla());
    }

    // GET solo tallas activas
    @GetMapping("/activas")
    public ResponseEntity<List<Talla>> getTallasActivas() {
        return ResponseEntity.ok(tallaService.getTallatrue());
    }

    // GET talla por ID
    @GetMapping("/Gettalla/{id}")
    public ResponseEntity<Talla> getTallaById(@PathVariable Long id) {
        return ResponseEntity.ok(tallaService.findById(id));
    }

    //  POST crear nueva talla
    @PostMapping("/Createtalla")
    public ResponseEntity<Talla> createTalla(@RequestBody Talla talla) {
        Talla nuevaTalla = tallaService.createTalla(talla);
        return new ResponseEntity<>(nuevaTalla, HttpStatus.CREATED);
    }

    // PUT actualizar talla
    @PutMapping("/updatetalla/{id}")
    public ResponseEntity<Talla> updateTalla(@RequestBody Talla talla,
                                             @PathVariable Long id) {
        Talla tallaActualizada = tallaService.updateTalla(talla, id);
        return ResponseEntity.ok(tallaActualizada);
    }

    // DELETE (soft delete)
    @DeleteMapping("/deletetalla/{id}")
    public ResponseEntity<Void> deleteTalla(@PathVariable Long id) {
        tallaService.deleteTalla(id);
        return ResponseEntity.noContent().build();
    }
}
