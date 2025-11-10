package com.a3.prototipo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String url;
    
    private Boolean isMalicious;
    private String riskLevel;
    private Double confidence;
    private String details;
    
    // Campos para usuário (se logado)
    private String userEmail;
    
    // Campos para Gemini
    private String category;
    private String summary;
    private String keywords;
    
    private LocalDateTime validatedAt = LocalDateTime.now();
    
    // Construtores
    public Url() {}
    
    public Url(String url, Boolean isMalicious, String riskLevel, Double confidence, String details) {
        this.url = url;
        this.isMalicious = isMalicious;
        this.riskLevel = riskLevel;
        this.confidence = confidence;
        this.details = details;
    }
    
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public Boolean getIsMalicious() { return isMalicious; }
    public void setIsMalicious(Boolean isMalicious) { this.isMalicious = isMalicious; }
    
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    
    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }
    
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
    
    public LocalDateTime getValidatedAt() { return validatedAt; }
    public void setValidatedAt(LocalDateTime validatedAt) { this.validatedAt = validatedAt; }
}