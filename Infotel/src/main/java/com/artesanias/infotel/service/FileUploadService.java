package com.artesanias.infotel.service;


import com.artesanias.infotel.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB

    public String uploadFile(MultipartFile file, String subdirectory) {
        validateFile(file);

        try {
            // Create directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir, subdirectory);
            Files.createDirectories(uploadPath);

            // Generate unique filename
            String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
            String fileExtension = getFileExtension(originalFilename);
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Save file
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String fileUrl = "/api/files/" + subdirectory + "/" + uniqueFilename;
            log.info("File uploaded successfully: {}", fileUrl);

            return fileUrl;

        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new BadRequestException("Error al subir el archivo: " + e.getMessage());
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            if (fileUrl != null && fileUrl.startsWith("/api/files/")) {
                String relativePath = fileUrl.substring("/api/files/".length());
                Path filePath = Paths.get(uploadDir, relativePath);

                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    log.info("File deleted successfully: {}", fileUrl);
                }
            }
        } catch (IOException e) {
            log.error("Error deleting file: {}", e.getMessage());
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("El archivo está vacío");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("El archivo es demasiado grande. Máximo 10MB");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || filename.trim().isEmpty()) {
            throw new BadRequestException("Nombre de archivo inválido");
        }

        String fileExtension = getFileExtension(filename).toLowerCase();
        if (!isValidFileExtension(fileExtension)) {
            throw new BadRequestException("Tipo de archivo no permitido: " + fileExtension);
        }
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        return lastDotIndex > 0 ? filename.substring(lastDotIndex) : "";
    }

    private boolean isValidFileExtension(String extension) {
        String[] allowedExtensions = {
                ".jpg", ".jpeg", ".png", ".gif", ".webp", // Images
                ".glb", ".gltf", ".obj", ".fbx", ".blend" // 3D Models
        };

        for (String allowed : allowedExtensions) {
            if (allowed.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
