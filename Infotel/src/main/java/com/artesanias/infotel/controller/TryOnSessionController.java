package com.artesanias.infotel.controller;


import com.artesanias.infotel.dto.request.TryOnSessionRequest;
import com.artesanias.infotel.dto.response.ApiResponse;
import com.artesanias.infotel.dto.response.PageResponse;
import com.artesanias.infotel.dto.response.TryOnSessionResponse;
import com.artesanias.infotel.service.TryOnSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Try-On Sessions", description = "APIs para gestión de sesiones de prueba virtual")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TryOnSessionController {

    private final TryOnSessionService sessionService;

    @PostMapping
    @Operation(summary = "Crear sesión", description = "Crear nueva sesión de prueba virtual")
    public ResponseEntity<ApiResponse<TryOnSessionResponse>> createSession(
            @Valid @RequestBody TryOnSessionRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        TryOnSessionResponse session = sessionService.createSession(email, request);

        return ResponseEntity.ok(ApiResponse.success("Sesión creada exitosamente", session));
    }

    @GetMapping
    @Operation(summary = "Mis sesiones", description = "Obtener sesiones del usuario autenticado")
    public ResponseEntity<ApiResponse<PageResponse<TryOnSessionResponse>>> getUserSessions(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String email = authentication.getName();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PageResponse<TryOnSessionResponse> sessions = sessionService.getUserSessions(email, pageable);

        return ResponseEntity.ok(ApiResponse.success(sessions));
    }

    @GetMapping("/screenshots")
    @Operation(summary = "Mis capturas", description = "Obtener sesiones con capturas de pantalla")
    public ResponseEntity<ApiResponse<PageResponse<TryOnSessionResponse>>> getUserSessionsWithScreenshots(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String email = authentication.getName();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PageResponse<TryOnSessionResponse> sessions = sessionService.getUserSessionsWithScreenshots(email, pageable);

        return ResponseEntity.ok(ApiResponse.success(sessions));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sesión", description = "Obtener información detallada de una sesión")
    public ResponseEntity<ApiResponse<TryOnSessionResponse>> getSession(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();
        TryOnSessionResponse session = sessionService.getSession(id, email);

        return ResponseEntity.ok(ApiResponse.success(session));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar sesión", description = "Eliminar una sesión de prueba virtual")
    public ResponseEntity<ApiResponse<String>> deleteSession(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();
        sessionService.deleteSession(id, email);

        return ResponseEntity.ok(ApiResponse.success("Sesión eliminada exitosamente"));
    }

    @GetMapping("/stats")
    @Operation(summary = "Estadísticas", description = "Obtener estadísticas generales de sesiones")
    public ResponseEntity<ApiResponse<Object[]>> getSessionStatistics() {
        Object[] stats = sessionService.getSessionStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/stats/my-average-duration")
    @Operation(summary = "Mi duración promedio", description = "Obtener duración promedio de mis sesiones")
    public ResponseEntity<ApiResponse<Double>> getMyAverageSessionDuration(Authentication authentication) {
        String email = authentication.getName();
        Double avgDuration = sessionService.getUserAverageSessionDuration(email);
        return ResponseEntity.ok(ApiResponse.success(avgDuration));
    }
}
