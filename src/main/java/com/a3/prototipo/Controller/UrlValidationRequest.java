package com.a3.prototipo.Controller;

public class UrlValidationRequest {
    private String url;

    
    public UrlValidationRequest() {}

    public UrlValidationRequest(String url) {
        this.url = url;
    }

    
    public String getUrl() { 
        return url; 
    }
    
    public void setUrl(String url) { 
        this.url = url; 
    }
    
    // Método toString para debugging
    @Override
    public String toString() {
        return "UrlValidationRequest{" +
                "url='" + url + '\'' +
                '}';
    }
}