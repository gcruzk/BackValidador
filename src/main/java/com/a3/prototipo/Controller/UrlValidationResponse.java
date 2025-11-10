package com.a3.prototipo.Controller;

public class UrlValidationResponse {
    private boolean isMalicious;
    private String message;
    private String details;
    private Double confidence;
    private String riskLevel;
    
    public UrlValidationResponse(boolean isMalicious, String message, String details, Double confidence) {
        this.isMalicious = isMalicious;
        this.message = message;
        this.details = details;
        this.confidence = confidence;
        this.riskLevel = isMalicious ? "HIGH" : "LOW";
    }
    
    // Getters e Setters
    public boolean isMalicious() { return isMalicious; }
    public void setMalicious(boolean malicious) { isMalicious = malicious; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    
    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }
    
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
}