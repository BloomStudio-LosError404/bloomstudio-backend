package com.generation.Bloom_Studio.dto;

public class AuthResponse {
    private String token;
    private String email;
    private String role;
    private String nombre;

    public AuthResponse(String token, String email, String role, String nombre) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.nombre = nombre;
    }

    // Getters
    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public String getNombre() { return nombre; }
}
