package com.a3.prototipo.Controller;

public class GeminiAnalysisResponse {
    private String category;
    private String summary;
    private String keywords;
    
    public GeminiAnalysisResponse(String category, String summary, String keywords) {
        this.category = category;
        this.summary = summary;
        this.keywords = keywords;
    }
    
    // Getters e Setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
}