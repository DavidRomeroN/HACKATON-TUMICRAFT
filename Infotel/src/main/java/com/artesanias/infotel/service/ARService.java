package com.artesanias.infotel.service;

import com.artesanias.infotel.dto.ClothingItemDto;
import com.artesanias.infotel.dto.request.ARSessionRequest;
import com.artesanias.infotel.dto.response.ARSessionResponse;
import com.artesanias.infotel.model.ClothingItem;
import com.artesanias.infotel.repository.ClothingItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ARService {

    @Autowired
    private ClothingItemRepository clothingItemRepository;

    @Autowired
    private ImageProcessingService imageProcessingService;

    // Almacén en memoria para sesiones AR (en producción usar Redis)
    private final Map<String, ARSessionResponse> activeSessions = new ConcurrentHashMap<>();

    public ARSessionResponse createARSession(String sessionId, ARSessionRequest request) {
        ARSessionResponse session = ARSessionResponse.builder()
                .sessionId(sessionId)
                .userId(request.getUserId())
                .cameraEnabled(request.isCameraEnabled())
                .deviceType(request.getDeviceType())
                .resolution(request.getResolution())
                .createdAt(new Date())
                .status("ACTIVE")
                .build();

        activeSessions.put(sessionId, session);
        return session;
    }

    public String processClothingTryOn(String sessionId, Long clothingId, MultipartFile userImage) throws IOException {
        ARSessionResponse session = activeSessions.get(sessionId);
        if (session == null) {
            throw new RuntimeException("Session not found");
        }

        Optional<ClothingItem> clothingOpt = clothingItemRepository.findById(clothingId);
        if (!clothingOpt.isPresent()) {
            throw new RuntimeException("Clothing item not found");
        }

        ClothingItem clothing = clothingOpt.get();

        // Procesar la imagen del usuario si se proporciona
        String processedImageUrl = null;
        if (userImage != null && !userImage.isEmpty()) {
            processedImageUrl = imageProcessingService.processUserImage(userImage, clothing);
        }

        // Actualizar la sesión con la prenda seleccionada
        session.setCurrentClothingId(clothingId);
        session.setCurrentClothingName(clothing.getName());
        session.setProcessedImageUrl(processedImageUrl);
        session.setLastUpdated(new Date());

        return processedImageUrl != null ? processedImageUrl : "SUCCESS";
    }

    public List<ClothingItemDto> getAvailableClothing() {
        List<ClothingItem> items = clothingItemRepository.findByAvailableForARTrue();
        return items.stream()
                .map(this::convertToDto)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public ARSessionResponse getSessionStatus(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public void endARSession(String sessionId) {
        ARSessionResponse session = activeSessions.get(sessionId);
        if (session != null) {
            session.setStatus("ENDED");
            session.setEndedAt(new Date());
            // Mantener por un tiempo para historial, luego remover
            activeSessions.remove(sessionId);
        }
    }

    public String calibrateCamera(MultipartFile calibrationImage) throws IOException {
        // Procesar imagen de calibración para obtener parámetros de la cámara
        return imageProcessingService.calibrateCamera(calibrationImage);
    }

    private ClothingItemDto convertToDto(ClothingItem item) {
        return ClothingItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .category(item.getCategory())
                .color(item.getColor())
                .size(item.getSize())
                .imageUrl(item.getImageUrl())
                .modelUrl(item.getModelUrl())
                .price(item.getPrice())
                .arCompatible(item.getAvailableForAR())
                .tags(item.getTags())
                .build();
    }
}