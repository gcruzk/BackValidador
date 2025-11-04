package com.a3.prototipo.Controller;

public class UrlStatsResponse {
    private long total;
    private long malicious;

    // Construtor padrão (necessário para Spring)
    public UrlStatsResponse() {}

    // Adicione este construtor!
    public UrlStatsResponse(long total, long malicious) {
        this.total = total;
        this.malicious = malicious;
    }

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }

    public long getMalicious() { return malicious; }
    public void setMalicious(long malicious) { this.malicious = malicious; }
}