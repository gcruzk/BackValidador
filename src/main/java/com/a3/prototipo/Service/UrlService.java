package com.a3.prototipo.Service;

import com.a3.prototipo.Repository.UrlRepository;
import com.a3.prototipo.Controller.UrlValidationResponse;
import com.a3.prototipo.Model.Url;
import com.a3.prototipo.Controller.UrlStatsResponse;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final IaAgent iaAgent;

    public UrlService(UrlRepository urlRepository, IaAgent iaAgent) {
        this.urlRepository = urlRepository;
        this.iaAgent = iaAgent;
    }

    public UrlValidationResponse validateUrl(String url) {
        // Valida sintaxe da URL antes de analisar
        try {
            new URL(url);  // tenta criar objeto URL; se inválido, gera exceção
        } catch (MalformedURLException e) {
            // Retorna resposta informando URL inválida
            return new UrlValidationResponse(url, "URL Inválido");
        }

        // Se válido, segue com validação do agente IA
        boolean isMalicious = iaAgent.isMalicious(url);
        
        // Salva no banco
        Url entity = new Url(url, isMalicious);
        urlRepository.save(entity);

        // Retorna resultado da validação
        return new UrlValidationResponse(url, isMalicious ? "Malicioso" : "Confiável");
    }

    public UrlStatsResponse getStats() {
        long total = urlRepository.count();
        long malicious = urlRepository.countByIsMalicious(true);
        return new UrlStatsResponse(total, malicious);
    }
}
