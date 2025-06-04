package com.artesanias.infotel.controller;

import com.artesanias.infotel.dto.ClothingItemDto;
import com.artesanias.infotel.dto.request.ARSessionRequest;
import com.artesanias.infotel.dto.response.ARSessionResponse;
import com.artesanias.infotel.service.ARService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ar")
@CrossOrigin(origins = "*")
public class ARController {

    @Autowired
    private ARService arService;

    @PostMapping("/session/create")
    public ResponseEntity<ARSessionResponse> createARSession(@RequestBody ARSessionRequest request) {
        try {
            String sessionId = UUID.randomUUID().toString();
            ARSessionResponse response = arService.createARSession(sessionId, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/session/{sessionId}/try-on")
    public ResponseEntity<String> tryOnClothing(
            @PathVariable String sessionId,
            @RequestParam("clothingId") Long clothingId,
            @RequestParam(value = "image", required = false) MultipartFile userImage) {
        try {
            String result = arService.processClothingTryOn(sessionId, clothingId, userImage);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error processing try-on: " + e.getMessage());
        }
    }

    @GetMapping("/clothing")
    public ResponseEntity<List<ClothingItemDto>> getAvailableClothing() {
        List<ClothingItemDto> clothing = arService.getAvailableClothing();
        return ResponseEntity.ok(clothing);
    }

    @GetMapping("/session/{sessionId}/status")
    public ResponseEntity<ARSessionResponse> getSessionStatus(@PathVariable String sessionId) {
        ARSessionResponse session = arService.getSessionStatus(sessionId);
        if (session != null) {
            return ResponseEntity.ok(session);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/session/{sessionId}")
    public ResponseEntity<Void> endARSession(@PathVariable String sessionId) {
        arService.endARSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calibrate")
    public ResponseEntity<String> calibrateCamera(@RequestParam("image") MultipartFile calibrationImage) {
        try {
            String calibrationData = arService.calibrateCamera(calibrationImage);
            return ResponseEntity.ok(calibrationData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Calibration failed: " + e.getMessage());
        }
    }
}
