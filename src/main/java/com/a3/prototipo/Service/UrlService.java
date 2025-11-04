package com.a3.prototipo.Service;

import com.a3.prototipo.Repository.UrlRepository;
import com.a3.prototipo.Controller.UrlValidationResponse;
import com.a3.prototipo.Model.Url;
import com.a3.prototipo.Controller.UrlValidationRequest;
import org.springframework.stereotype.Service;
import com.a3.prototipo.Controller.UrlStatsResponse;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final IaAgent iaAgent;

    public UrlService(UrlRepository urlRepository, IaAgent iaAgent) {
        this.urlRepository = urlRepository;
        this.iaAgent = iaAgent;
    }

    public UrlValidationResponse validateUrl(String url) {
        boolean isMalicious = iaAgent.isMalicious(url);
        Url entity = new Url(url, isMalicious);
        urlRepository.save(entity);
        return new UrlValidationResponse(url, isMalicious ? "Malicioso" : "Confiável");
    }

    public UrlStatsResponse getStats() {
        long total = urlRepository.count();
        long malicious = urlRepository.countByIsMalicious(true);
        return new UrlStatsResponse(total, malicious);
    }
}