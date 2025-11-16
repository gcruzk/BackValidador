package com.a3.prototipo.Controller;

public class GeminiAnalysisResponse {
    private String category;
    private String summary;
    private String keywords;
    private String trustLevel;
    private String characteristics;
    
    // Construtor atualizado com 5 parâmetros
    public GeminiAnalysisResponse(String category, String summary, String keywords, String trustLevel, String characteristics) {
        this.category = category;
        this.summary = summary;
        this.keywords = keywords;
        this.trustLevel = trustLevel;
        this.characteristics = characteristics;
    }
    
    // Construtor alternativo com 3 parâmetros (para compatibilidade)
    public GeminiAnalysisResponse(String category, String summary, String keywords) {
        this.category = category;
        this.summary = summary;
        this.keywords = keywords;
        this.trustLevel = "N/A";
        this.characteristics = "N/A";
    }
    
    // Getters e Setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
    
    public String getTrustLevel() { return trustLevel; }
    public void setTrustLevel(String trustLevel) { this.trustLevel = trustLevel; }
    
    public String getCharacteristics() { return characteristics; }
    public void setCharacteristics(String characteristics) { this.characteristics = characteristics; }
}