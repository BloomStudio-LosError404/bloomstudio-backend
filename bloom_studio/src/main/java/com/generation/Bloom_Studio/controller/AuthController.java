package com.generation.Bloom_Studio.controller;

import com.generation.Bloom_Studio.dto.AuthResponse;
import com.generation.Bloom_Studio.dto.LoginRequest;
import com.generation.Bloom_Studio.model.Usuario;
import com.generation.Bloom_Studio.repository.UsuarioRepository;
import com.generation.Bloom_Studio.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository; // INYECCIÓN DE REPOSITORIO

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtService jwtService,
                            UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // 1. Autenticar
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // 2. Cargar detalles del usuario
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        // 3. Generar Token
        final String token = jwtService.generateToken(userDetails);

        // 4. Obtener el Rol
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .get().getAuthority();

        // 5. Buscar el nombre REAL del Usuario
        // Como ya pasó la autenticación, sabemos que el correo existe, así que usamos .get() o .orElse("")
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        String nombreReal = usuario.getNombre();

        // 6. ENVIAR TODO
        return ResponseEntity.ok(new AuthResponse(token, userDetails.getUsername(), role, nombreReal));
    }
}
