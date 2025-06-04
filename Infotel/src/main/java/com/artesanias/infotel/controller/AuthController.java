/*package com.artesanias.infotel.controller;


import com.artesanias.infotel.dto.request.LoginRequest;
import com.artesanias.infotel.dto.request.RegisterRequest;
import com.artesanias.infotel.dto.response.ApiResponse;
import com.artesanias.infotel.dto.response.AuthResponse;
import com.artesanias.infotel.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "APIs para autenticación de usuarios")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController{

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Autenticar usuario con email y contraseña")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        AuthResponse authResponse = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Inicio de sesión exitoso", authResponse));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario", description = "Crear una nueva cuenta de usuario")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        log.info("Registration attempt for email: {}", request.getEmail());

        AuthResponse authResponse = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("Registro exitoso", authResponse));
    }

    @PostMapping("/validate")
    @Operation(summary = "Validar token", description = "Verificar si un token JWT es válido")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestParam String token) { // <-- ¡Cambio de String a Boolean!
        log.info("Validating token: {}", token); // Para depuración

        boolean isValid = authService.validateToken(token); // <-- Ahora devuelve un boolean

        if (isValid) {
            log.info("Token is valid.");
            return ResponseEntity.ok(ApiResponse.success("Token válido", true)); // Envía true en el campo 'data'
        } else {
            log.warn("Token is invalid or expired.");
            // Devuelve 200 OK pero con un mensaje de error y 'false' en el data
            // Esto evita que el interceptor de Axios en el frontend active la redirección
            // y permite que useAuthStore maneje la lógica de 'logout' limpiamente.
            return ResponseEntity.ok(ApiResponse.error("Token inválido o expirado", false));
        }
    }
}*/