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

import java.util.HashMap;
import java.util.Map;

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
        UrlValidationResponse response = urlService.validateUrl(request.getUrl(), null);
        return ResponseEntity.ok(response);
    }
    
    // Validação de URL com autenticação
    @PostMapping("/validate-auth")
    public ResponseEntity<?> validateUrlWithAuth(
            @RequestBody UrlValidationRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        String userEmail = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                userEmail = jwtService.extractUsername(token);
            } catch (Exception e) {
                System.err.println("Erro ao extrair email do token: " + e.getMessage());
            }
        }
        
        UrlValidationResponse response = urlService.validateUrl(request.getUrl(), userEmail);
        
        // Se usuário logado, incluir análise Gemini na resposta
        if (userEmail != null) {
            Map<String, Object> fullResponse = new HashMap<>();
            fullResponse.put("maliciousAnalysis", response);
            try {
                GeminiAnalysisResponse geminiResponse = geminiService.analyzeUrl(request.getUrl());
                fullResponse.put("geminiAnalysis", geminiResponse);
            } catch (Exception e) {
                fullResponse.put("geminiAnalysis", 
                    new GeminiAnalysisResponse("Erro", "Falha na análise: " + e.getMessage(), ""));
            }
            return ResponseEntity.ok(fullResponse);
        }
        
        return ResponseEntity.ok(response);
    }
    
    // Login
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // Registro
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest registerRequest) {
        try {
            com.a3.prototipo.Model.User user = new com.a3.prototipo.Model.User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword());
            com.a3.prototipo.Model.User savedUser = authService.register(user);
            return ResponseEntity.ok(Map.of("message", "Usuário registrado com sucesso", "email", savedUser.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Erro no registro: " + e.getMessage()));
        }
    }
    
    // Análise Gemini (requer autenticação)
    @PostMapping("/gemini/analyze")
    public ResponseEntity<?> analyzeWithGemini(@RequestBody Map<String, String> request) {
        try {
            String url = request.get("url");
            GeminiAnalysisResponse response = geminiService.analyzeUrl(url);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Erro na análise: " + e.getMessage()));
        }
    }
    
    // Estatísticas
    @GetMapping("/stats")
    public ResponseEntity<UnStatsResponse> getStats() {
        try {
            Long total = urlRepository.countTotalUrls();
            Long malicious = urlRepository.countMaliciousUrls();
            
            UnStatsResponse stats = new UnStatsResponse();
            stats.setTotal(total != null ? total : 0L);
            stats.setMalicious(malicious != null ? malicious : 0L);
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            // Retorna estatísticas zeradas em caso de erro
            return ResponseEntity.ok(new UnStatsResponse(0L, 0L));
        }
    }
}