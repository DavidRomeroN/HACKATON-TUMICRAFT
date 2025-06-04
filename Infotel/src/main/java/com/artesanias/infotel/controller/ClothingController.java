package com.artesanias.infotel.controller;


import com.artesanias.infotel.dto.request.ClothingUploadRequest;
import com.artesanias.infotel.dto.response.ApiResponse;
import com.artesanias.infotel.dto.response.ClothingResponse;
import com.artesanias.infotel.dto.response.PageResponse;
import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.ClothingCategory;
import com.artesanias.infotel.service.ClothingService;
import com.artesanias.infotel.service.PreferencesService;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/clothing")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Clothing", description = "APIs para gestión de ropa")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClothingController {

    private final ClothingService clothingService;
    private final PreferencesService preferencesService;

    @GetMapping
    @Operation(summary = "Listar ropa pública", description = "Obtener lista paginada de ropa pública")
    public ResponseEntity<ApiResponse<PageResponse<ClothingResponse>>> getAllPublicClothing(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        PageResponse<ClothingResponse> clothing = clothingService.getAllPublicClothing(pageable);

        return ResponseEntity.ok(ApiResponse.success(clothing));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Listar ropa por categoría", description = "Obtener ropa filtrada por categoría")
    public ResponseEntity<ApiResponse<PageResponse<ClothingResponse>>> getClothingByCategory(
            @PathVariable ClothingCategory category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PageResponse<ClothingResponse> clothing = clothingService.getClothingByCategory(category, pageable);

        return ResponseEntity.ok(ApiResponse.success(clothing));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener prenda por ID", description = "Obtener información detallada de una prenda")
    public ResponseEntity<ApiResponse<ClothingResponse>> getClothingById(@PathVariable Long id) {
        ClothingResponse clothing = clothingService.getClothingById(id);
        return ResponseEntity.ok(ApiResponse.success(clothing));
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar ropa", description = "Buscar ropa con filtros avanzados")
    public ResponseEntity<ApiResponse<PageResponse<ClothingResponse>>> searchClothing(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ClothingCategory category,
            @RequestParam(required = false) Clothing.Size size,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Boolean isPublic,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        PageResponse<ClothingResponse> clothing = clothingService.searchClothing(
                name, category, size, color, isPublic, pageable
        );

        return ResponseEntity.ok(ApiResponse.success(clothing));
    }

    @GetMapping("/my-clothing")
    @Operation(summary = "Mi ropa", description = "Obtener ropa subida por el usuario autenticado")
    public ResponseEntity<ApiResponse<PageResponse<ClothingResponse>>> getMyClothing(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        String email = authentication.getName();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        PageResponse<ClothingResponse> clothing = clothingService.getUserClothing(email, pageable);

        return ResponseEntity.ok(ApiResponse.success(clothing));
    }

    @PostMapping("/upload")
    @Operation(summary = "Subir ropa", description = "Subir nueva prenda con imagen y modelo 3D")
    public ResponseEntity<ApiResponse<ClothingResponse>> uploadClothing(
            @Valid @RequestPart("clothing") ClothingUploadRequest request,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart(value = "model", required = false) MultipartFile modelFile,
            Authentication authentication) {

        String email = authentication.getName();
        ClothingResponse clothing = clothingService.uploadClothing(request, imageFile, modelFile, email);

        return ResponseEntity.ok(ApiResponse.success("Prenda subida exitosamente", clothing));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar prenda", description = "Actualizar información de una prenda")
    public ResponseEntity<ApiResponse<ClothingResponse>> updateClothing(
            @PathVariable Long id,
            @Valid @RequestBody ClothingUploadRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        ClothingResponse clothing = clothingService.updateClothing(id, request, email);

        return ResponseEntity.ok(ApiResponse.success("Prenda actualizada exitosamente", clothing));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar prenda", description = "Eliminar una prenda del usuario")
    public ResponseEntity<ApiResponse<String>> deleteClothing(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();
        clothingService.deleteClothing(id, email);

        return ResponseEntity.ok(ApiResponse.success("Prenda eliminada exitosamente"));
    }

    @PostMapping("/{id}/select")
    @Operation(summary = "Seleccionar prenda", description = "Marcar prenda como última seleccionada")
    public ResponseEntity<ApiResponse<String>> selectClothing(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();
        preferencesService.updateLastSelectedClothing(email, id);

        return ResponseEntity.ok(ApiResponse.success("Prenda seleccionada"));
    }

    @GetMapping("/{id}/similar")
    @Operation(summary = "Ropa similar", description = "Obtener ropa similar a una prenda específica")
    public ResponseEntity<ApiResponse<List<ClothingResponse>>> getSimilarClothing(
            @PathVariable Long id,
            @RequestParam(defaultValue = "6") int limit) {

        List<ClothingResponse> similarClothing = clothingService.getSimilarClothing(id, limit);
        return ResponseEntity.ok(ApiResponse.success(similarClothing));
    }

    @GetMapping("/popular")
    @Operation(summary = "Ropa popular", description = "Obtener ropa más utilizada")
    public ResponseEntity<ApiResponse<PageResponse<ClothingResponse>>> getPopularClothing(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        Pageable pageable = PageRequest.of(page, size);
        PageResponse<ClothingResponse> clothing = clothingService.getPopularClothing(pageable);

        return ResponseEntity.ok(ApiResponse.success(clothing));
    }

    @GetMapping("/categories")
    @Operation(summary = "Listar categorías", description = "Obtener todas las categorías de ropa disponibles")
    public ResponseEntity<ApiResponse<ClothingCategory[]>> getCategories() {
        return ResponseEntity.ok(ApiResponse.success(ClothingCategory.values()));
    }

    @GetMapping("/sizes")
    @Operation(summary = "Listar tallas", description = "Obtener todas las tallas disponibles")
    public ResponseEntity<ApiResponse<Clothing.Size[]>> getSizes() {
        return ResponseEntity.ok(ApiResponse.success(Clothing.Size.values()));
    }
}