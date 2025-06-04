package com.artesanias.infotel.controller;

import com.artesanias.infotel.dto.response.ApiResponse;
import com.artesanias.infotel.dto.response.UserResponse;
import com.artesanias.infotel.model.User;
import com.artesanias.infotel.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Users", description = "APIs para gestión de usuarios")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(summary = "Obtener perfil actual", description = "Obtener información del usuario autenticado")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserResponse user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "Obtener información de un usuario específico")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtener lista paginada de usuarios")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserResponse> users = userService.getAllUsers(pageable);

        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar usuarios", description = "Buscar usuarios por nombre")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> searchUsers(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<UserResponse> users = userService.searchUsers(name, pageable);

        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PutMapping("/me")
    @Operation(summary = "Actualizar perfil", description = "Actualizar información del usuario autenticado")
    public ResponseEntity<ApiResponse<UserResponse>> updateCurrentUser(
            @RequestBody User updateRequest,
            Authentication authentication) {

        String email = authentication.getName();
        UserResponse currentUser = userService.getUserByEmail(email);
        UserResponse updatedUser = userService.updateUser(currentUser.getId(), updateRequest);

        return ResponseEntity.ok(ApiResponse.success("Perfil actualizado exitosamente", updatedUser));
    }

    @DeleteMapping("/me")
    @Operation(summary = "Eliminar cuenta", description = "Desactivar la cuenta del usuario autenticado")
    public ResponseEntity<ApiResponse<String>> deleteCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserResponse currentUser = userService.getUserByEmail(email);
        userService.deleteUser(currentUser.getId());

        return ResponseEntity.ok(ApiResponse.success("Cuenta desactivada exitosamente"));
    }

    @GetMapping("/stats/total")
    @Operation(summary = "Total de usuarios activos", description = "Obtener el número total de usuarios activos")
    public ResponseEntity<ApiResponse<Long>> getTotalActiveUsers() {
        Long total = userService.getTotalActiveUsers();
        return ResponseEntity.ok(ApiResponse.success(total));
    }
}
