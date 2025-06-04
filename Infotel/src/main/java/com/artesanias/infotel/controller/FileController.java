package com.artesanias.infotel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/files")
@Slf4j
@Tag(name = "Files", description = "APIs para servir archivos estáticos")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/{subdirectory}/{filename:.+}")
    @Operation(summary = "Obtener archivo", description = "Servir archivos estáticos (imágenes, modelos 3D, etc.)")
    public ResponseEntity<Resource> getFile(
            @PathVariable String subdirectory,
            @PathVariable String filename) {

        try {
            Path filePath = Paths.get(uploadDir).resolve(subdirectory).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = getContentType(filename);

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (Exception e) {
            log.error("Error serving file: {}/{} - {}", subdirectory, filename, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private String getContentType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();

        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            case "glb" -> "model/gltf-binary";
            case "gltf" -> "model/gltf+json";
            case "obj" -> "application/x-wavefront-obj";
            case "fbx" -> "application/octet-stream";
            default -> "application/octet-stream";
        };
    }
}
