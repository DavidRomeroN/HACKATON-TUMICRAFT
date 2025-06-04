package com.artesanias.infotel.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ARWebSocketController {

    @MessageMapping("/ar/frame")
    @SendToUser("/queue/ar/processed")
    public Map<String, Object> processFrame(Map<String, Object> frameData) {
        // Procesar frame en tiempo real
        String sessionId = (String) frameData.get("sessionId");
        String imageData = (String) frameData.get("imageData");
        Long clothingId = ((Number) frameData.get("clothingId")).longValue();

        // Aquí iría el procesamiento real de la imagen
        // Por ahora retornamos los datos simulados

        return Map.of(
                "sessionId", sessionId,
                "processedImageUrl", "data:image/jpeg;base64,processed_image_data",
                "timestamp", System.currentTimeMillis(),
                "success", true
        );
    }

    @MessageMapping("/ar/calibrate")
    @SendToUser("/queue/ar/calibration")
    public Map<String, Object> calibrateCamera(Map<String, Object> calibrationData) {
        String sessionId = (String) calibrationData.get("sessionId");

        return Map.of(
                "sessionId", sessionId,
                "calibrationResult", "success",
                "parameters", Map.of(
                        "focalLength", 800.0,
                        "principalPoint", new double[]{640, 360},
                        "distortion", new double[]{0.1, -0.2, 0.0, 0.0}
                )
        );
    }
}
