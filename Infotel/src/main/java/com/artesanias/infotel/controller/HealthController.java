package com.artesanias.infotel.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
@Tag(name = "Health", description = "APIs para verificar estado del sistema")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HealthController {

    @GetMapping
    @Operation(summary = "Health Check", description = "Verificar que la API esté funcionando")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "Virtual Try-On API");
        response.put("version", "1.0.0");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ping")
    @Operation(summary = "Ping", description = "Endpoint simple para verificar conectividad")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
