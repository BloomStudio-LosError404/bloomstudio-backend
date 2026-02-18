package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.dto.CambioPasswordDto;
import com.generation.Bloom_Studio.dto.SolicitudRecuperacionDto;
import com.generation.Bloom_Studio.service.RecuperacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Usamos /auth porque debe ser público
@CrossOrigin(origins = "*")
public class RecuperacionController {

    private final RecuperacionService recuperacionService;

    @Autowired
    public RecuperacionController(RecuperacionService recuperacionService) {
        this.recuperacionService = recuperacionService;
    }

    // 1. Solicitar el correo de recuperación
    @PostMapping("/recuperar-password")
    public ResponseEntity<String> solicitarRecuperacion(@RequestBody SolicitudRecuperacionDto dto) {
        try {
            recuperacionService.generarTokenRecuperacion(dto.getEmail());
            // Por seguridad, siempre decimos "Si el correo existe, se envió" para no dar pistas a hackers
            return ResponseEntity.ok("Si el correo existe, se han enviado las instrucciones.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Ejecutar el cambio de contraseña
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody CambioPasswordDto dto) {
        try {
            recuperacionService.cambiarPassword(dto);
            return ResponseEntity.ok("Contraseña actualizada correctamente. Ya puedes iniciar sesión.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
