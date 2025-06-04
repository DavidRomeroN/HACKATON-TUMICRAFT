package com.artesanias.infotel.service;

import com.artesanias.infotel.dto.request.PreferencesUpdateRequest;
import com.artesanias.infotel.dto.response.PreferencesResponse;
import com.artesanias.infotel.exception.ResourceNotFoundException;
import com.artesanias.infotel.model.User;
import com.artesanias.infotel.model.UserPreferences;
import com.artesanias.infotel.repository.UserPreferencesRepository;
import com.artesanias.infotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PreferencesService {

    private final UserPreferencesRepository preferencesRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final UserPreferencesInitializerService preferencesInitializerService; // <-- Inject the new service

    @Transactional(readOnly = true)
    public PreferencesResponse getUserPreferences(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserPreferences preferences = preferencesRepository.findByUser(user)
                // Use the new service to create default preferences
                .orElseGet(() -> preferencesInitializerService.createDefaultPreferences(user));

        PreferencesResponse response = modelMapper.map(preferences, PreferencesResponse.class);

        // Parse JSON fields
        try {
            if (preferences.getPreferredColors() != null) {
                response.setPreferredColors(objectMapper.readValue(
                        preferences.getPreferredColors(),
                        List.class
                ));
            }
            if (preferences.getFavoriteCategories() != null) {
                response.setFavoriteCategories(objectMapper.readValue(
                        preferences.getFavoriteCategories(),
                        List.class
                ));
            }
        } catch (JsonProcessingException e) {
            log.warn("Error parsing JSON preferences for user {}: {}", userEmail, e.getMessage());
        }

        return response;
    }

    @Transactional
    public PreferencesResponse updateUserPreferences(String userEmail, PreferencesUpdateRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Use the new initializer service here as well for consistency
        UserPreferences preferences = preferencesRepository.findByUser(user)
                .orElseGet(() -> preferencesInitializerService.createDefaultPreferences(user));

        // Update fields
        if (request.getAvatarType() != null) {
            preferences.setAvatarType(request.getAvatarType());
        }
        if (request.getAvatarSkinTone() != null) {
            preferences.setAvatarSkinTone(request.getAvatarSkinTone());
        }
        if (request.getBodyType() != null) {
            preferences.setBodyType(request.getBodyType());
        }
        if (request.getHeight() != null) {
            preferences.setHeight(request.getHeight());
        }
        if (request.getWeight() != null) {
            preferences.setWeight(request.getWeight());
        }
        if (request.getPreferredSize() != null) {
            preferences.setPreferredSize(request.getPreferredSize());
        }
        if (request.getEnableCameraByDefault() != null) {
            preferences.setEnableCameraByDefault(request.getEnableCameraByDefault());
        }
        if (request.getEnablePoseDetection() != null) {
            preferences.setEnablePoseDetection(request.getEnablePoseDetection());
        }
        if (request.getLanguage() != null) {
            preferences.setLanguage(request.getLanguage());
        }

        // Convert lists to JSON
        try {
            if (request.getPreferredColors() != null) {
                preferences.setPreferredColors(objectMapper.writeValueAsString(request.getPreferredColors()));
            }
            if (request.getFavoriteCategories() != null) {
                preferences.setFavoriteCategories(objectMapper.writeValueAsString(request.getFavoriteCategories()));
            }
        } catch (JsonProcessingException e) {
            log.error("Error serializing preferences to JSON: {}", e.getMessage());
            throw new RuntimeException("Error updating preferences");
        }

        UserPreferences savedPreferences = preferencesRepository.save(preferences);

        log.info("Preferences updated for user: {}", userEmail);
        return modelMapper.map(savedPreferences, PreferencesResponse.class);
    }

    @Transactional
    public void updateLastSelectedClothing(String userEmail, Long clothingId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Use the new initializer service here as well for consistency
        UserPreferences preferences = preferencesRepository.findByUser(user)
                .orElseGet(() -> preferencesInitializerService.createDefaultPreferences(user));

        preferences.setLastSelectedClothingId(clothingId);
        preferencesRepository.save(preferences);

        log.info("Last selected clothing updated for user {}: {}", userEmail, clothingId);
    }

    // This private method is no longer needed in this class, as it's moved
    // private UserPreferences createDefaultPreferences(User user) { ... }
}