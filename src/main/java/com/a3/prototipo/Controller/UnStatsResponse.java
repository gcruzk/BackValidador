package com.a3.prototipo.Controller;

public class UnStatsResponse {
    private Long total;
    private Long malicious;
    
    
    public UnStatsResponse() {}
    
    // Construtor com parâmetros
    public UnStatsResponse(Long total, Long malicious) {
        this.total = total;
        this.malicious = malicious;
    }
    
    
    public Long getTotal() {
        return total;
    }
    
    public void setTotal(Long total) {
        this.total = total;
    }
    
    public Long getMalicious() {
        return malicious;
    }
    
    public void setMalicious(Long malicious) {
        this.malicious = malicious;
    }
    
    // Método toString para debugging
    @Override
    public String toString() {
        return "UnStatsResponse{" +
                "total=" + total +
                ", malicious=" + malicious +
                '}';
    }
}