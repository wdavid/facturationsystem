package com.facturation.system.facturationsystem.dtos;

public class AuthResponse {
    private String token;

    // Constructor sin argumentos (necesario para JPA/Jackson)
    public AuthResponse() {
    }

    // Constructor con argumento
    public AuthResponse(String token) {
        this.token = token;
    }

    // Getter y Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
