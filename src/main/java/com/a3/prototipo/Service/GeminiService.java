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
      
        return simulateGeminiAnalysis(url);
    }
    
    private GeminiAnalysisResponse simulateGeminiAnalysis(String url) {
        Random random = new Random(url.hashCode());
        
       
        UrlAnalysis[] analyses = {
            // Notícias
            new UrlAnalysis(
                "Notícias e Jornalismo",
                "Portal de notícias com cobertura jornalística abrangente. Apresenta artigos atualizados frequentemente sobre política, economia, esportes e entretenimento. O site mantém um padrão editorial profissional com múltiplas fontes e verificação de fatos.",
                "jornalismo, reportagens, atualidades, política, economia, esportes, cultura, notícias 24h, imprensa, redação",
                "🟢 Conteúdo geralmente confiável",
                "Alto tráfego, atualização frequente"
            ),
            
            
            new UrlAnalysis(
                "E-commerce e Varejo Online",
                "Plataforma de comércio eletrônico com amplo catálogo de produtos. Oferece sistema de pagamento seguro, avaliações de clientes e política de devolução. Especializado em vendas B2C com logística eficiente e suporte ao cliente.",
                "loja virtual, compras online, ecommerce, produtos, vendas, frete, pagamento, carrinho, ofertas, cupons",
                "⚠️ Verificar reputação da loja",
                "Métodos de pagamento criptografados"
            ),
            
           
            new UrlAnalysis(
                "Rede Social e Comunidade",
                "Plataforma de mídia social que permite compartilhamento de conteúdo, interação entre usuários e formação de comunidades. Inclui features como feed de notícias, mensagens privadas e sistema de seguidores.",
                "social media, rede social, compartilhamento, posts, followers, comunidade, interação, feed, mensagens, perfil",
                "🔴 Cuidado com informações pessoais",
                "Alto engajamento, conteúdo gerado por usuários"
            ),
            
            
            new UrlAnalysis(
                "Blog e Conteúdo Especializado",
                "Site de blog com conteúdo nichado e artigos aprofundados. Apresenta opiniões especializadas, tutoriais e análises detalhadas. Possui arquivo organizado e sistema de comentários para interação com leitores.",
                "blog, artigos, opinião, tutorial, conteúdo, nicho, especializado, escrita, leitura, comunidade",
                "🟡 Avaliar credibilidade do autor",
                "Conteúdo original, atualização regular"
            ),
            
           
            new UrlAnalysis(
                "Educação e Aprendizado",
                "Plataforma educacional com recursos de aprendizado online. Oferece cursos, materiais didáticos, videoaulas e exercícios interativos. Foca em educação formal ou complementar com certificação reconhecida.",
                "educação, cursos, aprendizado, escola, universidade, conhecimento, estudo, aulas, material didático, certificado",
                "🟢 Conteúdo educativo confiável",
                "Estrutura pedagógica organizada"
            ),
            
           
            new UrlAnalysis(
                "Governo e Serviços Públicos",
                "Portal governamental oficial que disponibiliza serviços públicos, informações institucionais e canais de atendimento. Oferece acesso a documentos, formulários e atualizações legais de forma segura e verificada.",
                "governo, serviços públicos, oficial, institucional, documentos, formulários, legislação, cidadania, informações",
                "🟢 Fonte oficial e verificada",
                "Alta confiabilidade, atualização oficial"
            ),
            
            
            new UrlAnalysis(
                "Entretenimento e Mídia",
                "Site de entretenimento com conteúdo multimídia diversificado. Inclui vídeos, jogos, streaming e conteúdo interativo. Foca em oferecer experiências de lazer e diversão para diferentes públicos.",
                "entretenimento, vídeos, jogos, streaming, diversão, lazer, mídia, conteúdo interativo, cultura, passatempo",
                "🟡 Verificar anúncios e pop-ups",
                "Conteúdo envolvente, interface dinâmica"
            ),
            
          
            new UrlAnalysis(
                "Tecnologia e Inovação",
                "Portal especializado em tecnologia, inovação e tendências digitais. Cobre lançamentos de produtos, reviews, análises de mercado e tutoriais técnicos. Atualizado com as últimas novidades do setor.",
                "tecnologia, inovação, gadgets, reviews, digital, TI, software, hardware, startups, tendências",
                "🟢 Conteúdo técnico especializado",
                "Atualização constante, linguagem técnica"
            ),
            
           
            new UrlAnalysis(
                "Saúde e Bem-estar",
                "Site dedicado a informações sobre saúde, bem-estar e qualidade de vida. Oferece artigos médicos revisados, dicas de exercícios, orientações nutricionais e notícias sobre pesquisas científicas.",
                "saúde, medicina, bem-estar, exercícios, nutrição, fitness, qualidade de vida, cuidados, prevenção, dicas",
                "⚠️ Consultar profissional para diagnósticos",
                "Informações revisadas, linguagem acessível"
            ),
            
           
            new UrlAnalysis(
                "Finanças e Investimentos",
                "Plataforma financeira com informações sobre mercado, investimentos e economia. Oferece ferramentas de análise, notícias do mercado financeiro e educacional sobre gestão de recursos.",
                "finanças, investimentos, economia, mercado, ações, bolsa, dinheiro, poupança, crédito, planejamento",
                "🔴 Verificar regulamentação",
                "Dados em tempo real, análise profissional"
            ),

           

            
            new UrlAnalysis(
                "Conteúdo Adulto +18",
                "Site destinado a público adulto com conteúdo restrito para maiores de 18 anos. Inclui material sensível, explícito ou destinado a audiência madura. Requer verificação de idade para acesso.",
                "adulto, +18, restrito, conteúdo sensível, explícito, maduro, verificação de idade, NSFW",
                "🔴 Acesso restrito a maiores de 18 anos",
                "Verificação de idade necessária"
            ),

            
            new UrlAnalysis(
                "Apostas e Cassino Online",
                "Plataforma de jogos de azar, apostas esportivas ou cassino virtual. Oferece modalidades como poker, caça-níqueis, apostas em eventos esportivos. Sujeito a regulamentações específicas por região.",
                "apostas, cassino, jogos de azar, poker, caça-níqueis, apostas esportivas, betting, gambling, torneios",
                "🔴 Verificar legalidade na sua região",
                "Idade mínima: 18-21 anos dependendo da jurisdição"
            ),

    
            new UrlAnalysis(
                "Jogos Online e Gaming",
                "Plataforma dedicada a jogos online, seja para download, streaming ou jogabilidade no navegador. Inclui jogos single-player, multiplayer, competitivos e casuais.",
                "jogos, gaming, online, multiplayer, competitivo, download, streaming, entretenimento digital, esports",
                "🟡 Verificar sistema de pagamentos",
                "Comunidade ativa, atualizações frequentes"
            ),

            
            new UrlAnalysis(
                "Download de Software e Aplicativos",
                "Site para download de programas, aplicativos, utilitários e ferramentas digitais. Oferece versões gratuitas, trial ou pagas de software para diversos propósitos.",
                "download, software, aplicativos, programas, utilitários, ferramentas, instalação, trial, gratuito, pago",
                "⚠️ Verificar origem do software",
                "Verificação de malware recomendada"
            ),

            
            new UrlAnalysis(
                "Fórum e Comunidade de Discussão",
                "Plataforma de discussão baseada em tópicos onde usuários podem criar threads, responder e interagir sobre assuntos específicos. Moderação variável dependendo da comunidade.",
                "fórum, discussão, comunidade, tópicos, threads, debate, opinião, moderação, usuários, interação",
                "🟡 Qualidade do conteúdo varia",
                "Conteúgo gerado pelos usuários"
            ),

            new UrlAnalysis(
                "Religião e Espiritualidade",
                "Site dedicado a temas religiosos, espirituais ou filosóficos. Pode incluir textos sagrados, orientações doutrinárias, comunidades de fé e recursos para prática religiosa.",
                "religião, espiritualidade, fé, filosofia, doutrina, sagrado, comunidade, orientação, prática, crenças",
                "🟢 Conteúdo geralmente seguro",
                "Foco em valores e comunidade"
            ),

            new UrlAnalysis(
                "Conteúdo Político e Ativismo",
                "Plataforma com foco em discussões políticas, ativismo, campanhas ou posicionamentos ideológicos. Pode conter opiniões fortes e conteúdo potencialmente polarizador.",
                "política, ativismo, ideologia, campanha, eleições, governo, debate, opinião, posicionamento, militância",
                "🟡 Conteúdo potencialmente polarizador",
                "Verificar múltiplas fontes recomendado"
            ),

            
            new UrlAnalysis(
                "Informações Médicas e de Saúde",
                "Site com informações detalhadas sobre condições médicas, tratamentos, medicamentos e orientações de saúde. Pode incluir sintomas, diagnósticos e recomendações terapêuticas.",
                "medicina, saúde, tratamento, sintomas, diagnóstico, medicamentos, doenças, condição médica, terapêutica",
                "⚠️ Não substitui consulta médica",
                "Informações para referência apenas"
            ),

           
            new UrlAnalysis(
                "Viagens e Turismo",
                "Plataforma especializada em planejamento de viagens, reservas de hospedagem, dicas de destinos e serviços turísticos. Oferece reviews, comparações de preços e guias de viagem.",
                "viagens, turismo, hospedagem, reservas, passagens, destinos, hotel, voos, guia, planejamento",
                "🟢 Serviço comercial padrão",
                "Verificar políticas de cancelamento"
            ),

            
            new UrlAnalysis(
                "Alimentação e Culinária",
                "Site dedicado a receitas, técnicas culinárias, reviews de restaurantes e conteúdo gastronômico. Inclui tutoriais, dicas de cozinha e comunidade de foodies.",
                "culinária, receitas, comida, restaurantes, gastronomia, cooking, foodie, ingredientes, técnicas, avaliações",
                "🟢 Conteúdo geralmente seguro",
                "Foco educativo e comunitário"
            )
        };
        
        int index = Math.abs(random.nextInt() % analyses.length);
        UrlAnalysis analysis = analyses[index];
        
        return new GeminiAnalysisResponse(
            analysis.category,
            analysis.summary,
            analysis.keywords,
            analysis.trustLevel,
            analysis.characteristics
        );
    }
    
    // Classe auxiliar para organizar as análises
    private static class UrlAnalysis {
        String category;
        String summary;
        String keywords;
        String trustLevel;
        String characteristics;
        
        UrlAnalysis(String category, String summary, String keywords, String trustLevel, String characteristics) {
            this.category = category;
            this.summary = summary;
            this.keywords = keywords;
            this.trustLevel = trustLevel;
            this.characteristics = characteristics;
        }
    }
}