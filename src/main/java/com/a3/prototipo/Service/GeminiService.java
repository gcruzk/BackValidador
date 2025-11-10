package com.a3.prototipo.Service;

import com.a3.prototipo.Controller.GeminiAnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GeminiService {
    
    @Value("${gemini.api.key:demo}")
    private String apiKey;
    
    public GeminiAnalysisResponse analyzeUrl(String url) {
        // Simulação para demo - em produção, integre com a API real do Gemini
        return simulateGeminiAnalysis(url);
    }
    
    private GeminiAnalysisResponse simulateGeminiAnalysis(String url) {
        Random random = new Random(url.hashCode());
        String[] categories = {"Notícias", "E-commerce", "Rede Social", "Blog", "Educacional", "Governo", "Entretenimento"};
        String[] summaries = {
            "Site de notícias com conteúdo atualizado frequentemente",
            "Loja online com diversos produtos à venda",
            "Plataforma de rede social para compartilhamento de conteúdo",
            "Blog pessoal ou corporativo com artigos informativos",
            "Site educacional com materiais de aprendizado",
            "Portal governamental com serviços públicos",
            "Site de entretenimento com vídeos e jogos"
        };
        String[] keywords = {
            "notícias, atualidades, jornalismo",
            "compras, produtos, ecommerce, vendas",
            "social, rede, compartilhamento, amigos",
            "blog, artigos, conteúdo, informação",
            "educação, aprendizado, cursos, escola",
            "governo, serviços, público, oficial",
            "entretenimento, vídeos, jogos, diversão"
        };
        
        int index = random.nextInt(categories.length);
        return new GeminiAnalysisResponse(
            categories[index],
            summaries[index],
            keywords[index]
        );
    }
}