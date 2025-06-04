package com.artesanias.infotel.dto.request;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ARSessionRequest {
    private String userId;
    private boolean cameraEnabled;
    private String deviceType; // "mobile", "desktop", "tablet"
    private String resolution; // "720p", "1080p", "4K"
    private Map<String, Object> cameraSettings;
}
