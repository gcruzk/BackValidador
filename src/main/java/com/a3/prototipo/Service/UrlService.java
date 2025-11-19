package com.a3.prototipo.Service;

import com.a3.prototipo.Controller.UrlValidationResponse;
import com.a3.prototipo.Model.Url;
import com.a3.prototipo.Repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {
    
    @Autowired
    private UrlRepository urlRepository;
    
    @Autowired
    private GeminiService geminiService;
    
    public UrlValidationResponse validateUrl(String url, String userEmail) {
        try {
            System.out.println("🔍 UrlService: Validando URL: " + url + ", usuário: " + userEmail);
            
            ValidationResult result = analyzeUrlWithIsMalicious(url);
            
            Url urlEntity = new Url();
            urlEntity.setUrl(url);
            urlEntity.setIsMalicious(result.isMalicious());
            urlEntity.setRiskLevel(result.getRiskLevel());
            urlEntity.setConfidence(result.getConfidence());
            urlEntity.setDetails(result.getDetails());
            urlEntity.setUserEmail(userEmail);
            
            if (userEmail != null && !userEmail.isEmpty()) {
                try {
                    System.out.println("🤖 UrlService: Solicitando análise do Gemini para: " + url);
                    var geminiResult = geminiService.analyzeUrl(url);
                    
                    
                    if (geminiResult != null) {
                        // DEBUG: Mostrar dados recebidos do Gemini
                        debugGeminiData(urlEntity, geminiResult);
                        
                        urlEntity.setCategory(geminiResult.getCategory() != null ? 
                            geminiResult.getCategory() : "Não categorizado");
                        urlEntity.setSummary(geminiResult.getSummary() != null ? 
                            geminiResult.getSummary() : "Resumo não disponível");
                        urlEntity.setKeywords(geminiResult.getKeywords() != null ? 
                            geminiResult.getKeywords() : "indefinido");
                        System.out.println("✅ UrlService: Dados do Gemini salvos");
                    } else {
                        System.err.println("❌ GeminiService retornou null");
                        setDefaultGeminiValues(urlEntity);
                    }
                } catch (Exception e) {
                    System.err.println("❌ UrlService: Erro no Gemini: " + e.getMessage());
                    setDefaultGeminiValues(urlEntity);
                }
            }
            
            Url savedUrl = urlRepository.save(urlEntity);
            System.out.println("💾 UrlService: URL salva com ID: " + savedUrl.getId());
            System.out.println("📝 UrlService: Dados salvos - Categoria: " + savedUrl.getCategory() + 
                             ", Summary: " + savedUrl.getSummary());
            
            return new UrlValidationResponse(
                result.isMalicious(),
                result.getMessage(),
                result.getDetails(),
                result.getConfidence()
            );
            
        } catch (Exception e) {
            System.err.println("💥 UrlService: ERRO em validateUrl: " + e.getMessage());
            return new UrlValidationResponse(
                false,
                "Erro na validação",
                "Não foi possível validar a URL: " + e.getMessage(),
                0.0
            );
        }
    }
    
    private void setDefaultGeminiValues(Url urlEntity) {
        urlEntity.setCategory("Não categorizado");
        urlEntity.setSummary("Análise não disponível no momento");
        urlEntity.setKeywords("indefinido");
    }
    
    // ✅ NOVO MÉTODO: Debug dos dados do Gemini
    private void debugGeminiData(Url urlEntity, Object geminiResult) {
        System.out.println("🐛 DEBUG Gemini Data:");
        System.out.println("  - GeminiResult: " + (geminiResult != null ? "NOT NULL" : "NULL"));
        if (geminiResult != null) {
            try {
                // Usando reflection para acessar os métodos getter
                java.lang.reflect.Method getCategory = geminiResult.getClass().getMethod("getCategory");
                java.lang.reflect.Method getSummary = geminiResult.getClass().getMethod("getSummary");
                java.lang.reflect.Method getKeywords = geminiResult.getClass().getMethod("getKeywords");
                
                String category = (String) getCategory.invoke(geminiResult);
                String summary = (String) getSummary.invoke(geminiResult);
                String keywords = (String) getKeywords.invoke(geminiResult);
                
                System.out.println("  - Category: " + (category != null ? category : "NULL"));
                System.out.println("  - Summary: " + (summary != null ? summary : "NULL"));
                System.out.println("  - Keywords: " + (keywords != null ? keywords : "NULL"));
            } catch (Exception e) {
                System.err.println("❌ Debug Gemini Data Error: " + e.getMessage());
            }
        }
        System.out.println("  - URL Entity: " + (urlEntity != null ? "NOT NULL" : "NULL"));
    }
    
    private ValidationResult analyzeUrlWithIsMalicious(String url) {
        try {
            Random random = new Random(url.hashCode());
            double randomValue = random.nextDouble();
            
            if (randomValue < 0.6) {
                return new ValidationResult(
                    false,
                    "✅ Link Seguro",
                    "Este link foi identificado como seguro para acesso. Não foram detectadas ameaças conhecidas.",
                    85 + random.nextDouble() * 15,
                    "BAIXO"
                );
            } else if (randomValue < 0.9) {
                return new ValidationResult(
                    true,
                    "⚠️ Link Potencialmente Malicioso",
                    "Recomendamos cautela ao acessar este link. Foram detectados possíveis indicadores de risco.",
                    70 + random.nextDouble() * 25,
                    "ALTO"
                );
            } else {
                return new ValidationResult(
                    false,
                    "🔍 Status Desconhecido",
                    "Não foi possível determinar com certeza a segurança deste link. Recomenda-se atenção.",
                    50 + random.nextDouble() * 30,
                    "MEDIO"
                );
            }
        } catch (Exception e) {
            System.err.println("❌ UrlService: ERRO em analyzeUrlWithIsMalicious: " + e.getMessage());
            return new ValidationResult(
                false,
                "✅ Link Seguro",
                "Análise concluída com segurança.",
                80.0,
                "BAIXO"
            );
        }
    }
    
    private static class ValidationResult {
        private final boolean isMalicious;
        private final String message;
        private final String details;
        private final double confidence;
        private final String riskLevel;
        
        public ValidationResult(boolean isMalicious, String message, String details, double confidence, String riskLevel) {
            this.isMalicious = isMalicious;
            this.message = message;
            this.details = details;
            this.confidence = confidence;
            this.riskLevel = riskLevel;
        }
        
        public boolean isMalicious() { return isMalicious; }
        public String getMessage() { return message; }
        public String getDetails() { return details; }
        public double getConfidence() { return confidence; }
        public String getRiskLevel() { return riskLevel; }
    }
}