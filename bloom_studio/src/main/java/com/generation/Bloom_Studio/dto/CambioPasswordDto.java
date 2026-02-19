package com.generation.Bloom_Studio.dto;

public class CambioPasswordDto {
    private String token;
    private String newPassword;

    // Getters y Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}
