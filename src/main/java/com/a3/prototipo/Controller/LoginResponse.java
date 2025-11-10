package com.a3.prototipo.Controller;

public class LoginResponse {
    private String token;
    private String email;
    private String message;
    
    public LoginResponse(String token, String email) {
        this.token = token;
        this.email = email;
        this.message = "Login realizado com sucesso";
    }
    
    // Getters e Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}