package com.artesanias.infotel.service;


import com.artesanias.infotel.repository.UserRepository;
import com.artesanias.infotel.dto.request.TryOnSessionRequest;
import com.artesanias.infotel.dto.response.TryOnSessionResponse;
import com.artesanias.infotel.dto.response.PageResponse;
import com.artesanias.infotel.exception.ResourceNotFoundException;
import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.TryOnSession;
import com.artesanias.infotel.model.User;
import com.artesanias.infotel.repository.ClothingRepository;
import com.artesanias.infotel.repository.TryOnSessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TryOnSessionService {

    private final TryOnSessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final ClothingRepository clothingRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public TryOnSessionResponse createSession(String userEmail, TryOnSessionRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Clothing clothing = clothingRepository.findById(request.getClothingId())
                .orElseThrow(() -> new ResourceNotFoundException("Prenda no encontrada"));

        TryOnSession session = new TryOnSession();
        session.setUser(user);
        session.setClothing(clothing);
        session.setSessionData(request.getPoseData());
        session.setDuration(request.getDuration());

        // Save screenshot if provided
        if (request.getScreenshot() != null && !request.getScreenshot().isEmpty()) {
            String screenshotUrl = saveScreenshot(request.getScreenshot());
            session.setScreenshotUrl(screenshotUrl);
        }

        TryOnSession savedSession = sessionRepository.save(session);

        log.info("Try-on session created for user {} with clothing {}", userEmail, clothing.getName());
        return modelMapper.map(savedSession, TryOnSessionResponse.class);
    }

    @Transactional(readOnly = true)
    public PageResponse<TryOnSessionResponse> getUserSessions(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Page<TryOnSession> sessionsPage = sessionRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        return convertToPageResponse(sessionsPage);
    }

    @Transactional(readOnly = true)
    public PageResponse<TryOnSessionResponse> getUserSessionsWithScreenshots(String userEmail, Pageable pageable) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Page<TryOnSession> sessionsPage = sessionRepository.findUserSessionsWithScreenshots(user, pageable);
        return convertToPageResponse(sessionsPage);
    }

    @Transactional(readOnly = true)
    public TryOnSessionResponse getSession(Long sessionId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        TryOnSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));

        // Check if user owns the session
        if (!session.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permisos para ver esta sesión");
        }

        return modelMapper.map(session, TryOnSessionResponse.class);
    }

    @Transactional
    public void deleteSession(Long sessionId, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        TryOnSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sesión no encontrada"));

        // Check if user owns the session
        if (!session.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permisos para eliminar esta sesión");
        }

        // Delete screenshot file if exists
        if (session.getScreenshotUrl() != null) {
            deleteScreenshot(session.getScreenshotUrl());
        }

        sessionRepository.delete(session);
        log.info("Try-on session {} deleted by user {}", sessionId, userEmail);
    }

    @Transactional(readOnly = true)
    public Object[] getSessionStatistics() {
        return sessionRepository.getSessionStatistics();
    }

    @Transactional(readOnly = true)
    public Double getUserAverageSessionDuration(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return sessionRepository.getAverageSessionDurationByUser(user);
    }

    private String saveScreenshot(String base64Screenshot) {
        try {
            // Remove data URL prefix if present
            String base64Data = base64Screenshot.replaceFirst("^data:image/[^;]+;base64,", "");

            // Decode base64
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            // Generate unique filename
            String filename = "screenshot_" + UUID.randomUUID().toString() + ".png";

            // Create screenshots directory if it doesn't exist
            Path screenshotsDir = Paths.get("uploads/screenshots");
            Files.createDirectories(screenshotsDir);

            // Save file
            Path filePath = screenshotsDir.resolve(filename);
            Files.write(filePath, imageBytes);

            String screenshotUrl = "/api/files/screenshots/" + filename;
            log.info("Screenshot saved: {}", screenshotUrl);

            return screenshotUrl;

        } catch (Exception e) {
            log.error("Error saving screenshot: {}", e.getMessage());
            throw new RuntimeException("Error al guardar la captura de pantalla");
        }
    }

    private void deleteScreenshot(String screenshotUrl) {
        try {
            if (screenshotUrl != null && screenshotUrl.startsWith("/api/files/screenshots/")) {
                String filename = screenshotUrl.substring("/api/files/screenshots/".length());
                Path filePath = Paths.get("uploads/screenshots", filename);

                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                    log.info("Screenshot deleted: {}", screenshotUrl);
                }
            }
        } catch (Exception e) {
            log.error("Error deleting screenshot: {}", e.getMessage());
        }
    }

    private PageResponse<TryOnSessionResponse> convertToPageResponse(Page<TryOnSession> sessionsPage) {
        Page<TryOnSessionResponse> responsePage = sessionsPage.map(session ->
                modelMapper.map(session, TryOnSessionResponse.class)
        );

        PageResponse<TryOnSessionResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(responsePage.getContent());
        pageResponse.setPageNumber(responsePage.getNumber());
        pageResponse.setPageSize(responsePage.getSize());
        pageResponse.setTotalElements(responsePage.getTotalElements());
        pageResponse.setTotalPages(responsePage.getTotalPages());
        pageResponse.setFirst(responsePage.isFirst());
        pageResponse.setLast(responsePage.isLast());
        pageResponse.setEmpty(responsePage.isEmpty());

        return pageResponse;
    }
}