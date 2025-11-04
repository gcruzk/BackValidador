package com.a3.prototipo.Controller;


import com.a3.prototipo.Service.UrlService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/urls")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/validate")
    public ResponseEntity<UrlValidationResponse> validateUrl(@RequestBody UrlValidationRequest request) {
        UrlValidationResponse response = urlService.validateUrl(request.getUrl());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats")
    public ResponseEntity<UrlStatsResponse> getStats() {
        UrlStatsResponse stats = urlService.getStats();
        return ResponseEntity.ok(stats);
    }
}