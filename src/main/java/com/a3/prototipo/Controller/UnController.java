package com.a3.prototipo.Controller;

import com.a3.prototipo.Model.Url;
import com.a3.prototipo.Repository.UrlRepository;
import com.a3.prototipo.Service.AuthService;
import com.a3.prototipo.Service.GeminiService;
import com.a3.prototipo.Service.JwtService;
import com.a3.prototipo.Service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;
import java.util.List;




@RestController
@RequestMapping("/api")
public class UnController {
    
    @Autowired
    private UrlService urlService;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private GeminiService geminiService;
    
    @Autowired
    private UrlRepository urlRepository;
    
    @Autowired
    private JwtService jwtService;
            
    // Validação de URL (pública)
    @PostMapping("/validate")
    public ResponseEntity<UrlValidationResponse> validateUrl(@RequestBody UrlValidationRequest request) {
        System.out.println("🌐 UnController: /validate chamado para URL: " + request.getUrl());
        UrlValidationResponse response = urlService.validateUrl(request.getUrl(), null);
        System.out.println("✅ UnController: Resposta /validate - Malicioso: " + response.isMalicious());
        return ResponseEntity.ok(response);
    }
    
    // Validação de URL com autenticação
    @PostMapping("/validate-auth")
    public ResponseEntity<?> validateUrlWithAuth(
        
            @RequestBody UrlValidationRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        System.out.println("VALIDATE-AUTH chamado com authHeader: " + authHeader);
        
        System.out.println("UnController: /validate-auth chamado para URL: " + request.getUrl());
        
        String userEmail = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                userEmail = jwtService.extractUsername(token);
                System.out.println(" UnController: Usuário autenticado: " + userEmail);
            } catch (Exception e) {
                System.err.println("❌ UnController: Erro ao extrair email do token: " + e.getMessage());
            }
        }
        
        
        UrlValidationResponse response = urlService.validateUrl(request.getUrl(), userEmail);
        
        // Se usuário logado, buscar dados completos do banco
        if (userEmail != null) {
            Map<String, Object> fullResponse = new HashMap<>();
            fullResponse.put("maliciousAnalysis", response);
            
            try {
                System.out.println("UnController: Buscando dados completos da URL salva");
                
                // Buscar a URL mais recente do usuário para obter dados Gemini
                List<Url> userUrls = urlRepository.findByUserEmail(userEmail);
                Url latestUrl = userUrls.stream()
                    .filter(url -> request.getUrl().equals(url.getUrl()))
                    .max(Comparator.comparing(Url::getValidatedAt))
                    .orElse(null);
                
                if (latestUrl != null && latestUrl.getCategory() != null) {
                    GeminiAnalysisResponse geminiResponse = new GeminiAnalysisResponse(
                        latestUrl.getCategory(),
                        latestUrl.getSummary(),
                        latestUrl.getKeywords(),
                        "N/A", 
                        "N/A" 
                    );
                    fullResponse.put("geminiAnalysis", geminiResponse);
                    System.out.println("UnController: Dados Gemini encontrados - Categoria: " + geminiResponse.getCategory());
                } else {
                    System.out.println("⚠️ UnController: URL salva sem dados Gemini, tentando análise direta");
                    try {
                        GeminiAnalysisResponse geminiResponse = geminiService.analyzeUrl(request.getUrl());
                        fullResponse.put("geminiAnalysis", geminiResponse);
                        System.out.println("✅ UnController: Análise Gemini direta concluída");
                    } catch (Exception geminiError) {
                        System.err.println("❌ UnController: Falha na análise Gemini: " + geminiError.getMessage());
                        fullResponse.put("geminiAnalysis", 
                            new GeminiAnalysisResponse("Erro", "Análise indisponível", "erro", "N/A", "N/A"));
                    }
                }
                
                System.out.println("UnController: Enviando resposta completa para usuário logado");
                return ResponseEntity.ok(fullResponse);
            } catch (Exception e) {
                System.err.println("❌ UnController: Erro ao processar dados Gemini: " + e.getMessage());
                fullResponse.put("geminiAnalysis", 
                    new GeminiAnalysisResponse("Erro", "Falha na análise: " + e.getMessage(), "erro", "N/A", "N/A"));
                return ResponseEntity.ok(fullResponse);
            }
        }
        
        System.out.println("📤 UnController: Enviando resposta básica para usuário não logado");
        return ResponseEntity.ok(response);
    }
    
    // Login
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        System.out.println("🔐 UnController: /auth/login chamado para: " + loginRequest.getEmail());
        try {
            LoginResponse response = authService.login(loginRequest);
            System.out.println("✅ UnController: Login bem-sucedido para: " + loginRequest.getEmail());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.err.println("❌ UnController: Erro no login: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Registro
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody LoginRequest registerRequest) {
        System.out.println("📝 UnController: /auth/register chamado para: " + registerRequest.getEmail());
        try {
            com.a3.prototipo.Model.User user = new com.a3.prototipo.Model.User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword());
            com.a3.prototipo.Model.User savedUser = authService.register(user);
            System.out.println("✅ UnController: Registro bem-sucedido para: " + registerRequest.getEmail());
            return ResponseEntity.ok(Map.of("message", "Usuário registrado com sucesso", "email", savedUser.getEmail()));
        } catch (Exception e) {
            System.err.println("❌ UnController: Erro no registro: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Erro no registro: " + e.getMessage()));
        }
    }
    
    // Análise Gemini (requer autenticação)
    @PostMapping("/gemini/analyze")
    public ResponseEntity<?> analyzeWithGemini(@RequestBody Map<String, String> request) {
        System.out.println("🤖 UnController: /gemini/analyze chamado");
        try {
            String url = request.get("url");
            System.out.println("🔍 UnController: Analisando URL com Gemini: " + url);
            GeminiAnalysisResponse response = geminiService.analyzeUrl(url);
            System.out.println("✅ UnController: Análise Gemini concluída - Categoria: " + response.getCategory());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("❌ UnController: Erro na análise Gemini: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Erro na análise: " + e.getMessage()));
        }
    }
    
    // Estatísticas
    @GetMapping("/stats")
    public ResponseEntity<UnStatsResponse> getStats() {
        System.out.println("📊 UnController: /stats chamado");
        try {
            Long total = urlRepository.countTotalUrls();
            Long malicious = urlRepository.countMaliciousUrls();
            
            UnStatsResponse stats = new UnStatsResponse();
            stats.setTotal(total != null ? total : 0L);
            stats.setMalicious(malicious != null ? malicious : 0L);
            
            System.out.println("✅ UnController: Estatísticas - Total: " + stats.getTotal() + ", Maliciosos: " + stats.getMalicious());
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("❌ UnController: Erro ao carregar estatísticas: " + e.getMessage());
            // Retorna estatísticas zeradas em caso de erro
            return ResponseEntity.ok(new UnStatsResponse(0L, 0L));
        }
    }
}