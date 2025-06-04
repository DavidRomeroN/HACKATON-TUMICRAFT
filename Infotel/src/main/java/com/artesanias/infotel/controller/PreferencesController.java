package com.artesanias.infotel.controller;

import com.artesanias.infotel.dto.request.PreferencesUpdateRequest;
import com.artesanias.infotel.dto.response.ApiResponse;
import com.artesanias.infotel.dto.response.PreferencesResponse;
import com.artesanias.infotel.service.PreferencesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Preferences", description = "APIs para gestión de preferencias de usuario")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PreferencesController {

    private final PreferencesService preferencesService;

    @GetMapping
    @Operation(summary = "Obtener preferencias", description = "Obtener preferencias del usuario autenticado")
    public ResponseEntity<ApiResponse<PreferencesResponse>> getUserPreferences(Authentication authentication) {
        String email = authentication.getName();
        PreferencesResponse preferences = preferencesService.getUserPreferences(email);
        return ResponseEntity.ok(ApiResponse.success(preferences));
    }

    @PutMapping
    @Operation(summary = "Actualizar preferencias", description = "Actualizar preferencias del usuario")
    public ResponseEntity<ApiResponse<PreferencesResponse>> updateUserPreferences(
            @Valid @RequestBody PreferencesUpdateRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        PreferencesResponse preferences = preferencesService.updateUserPreferences(email, request);

        return ResponseEntity.ok(ApiResponse.success("Preferencias actualizadas exitosamente", preferences));
    }

    @PostMapping("/last-clothing/{clothingId}")
    @Operation(summary = "Actualizar última prenda", description = "Actualizar la última prenda seleccionada")
    public ResponseEntity<ApiResponse<String>> updateLastSelectedClothing(
            @PathVariable Long clothingId,
            Authentication authentication) {

        String email = authentication.getName();
        preferencesService.updateLastSelectedClothing(email, clothingId);

        return ResponseEntity.ok(ApiResponse.success("Última prenda actualizada"));
    }
}
