package com.artesanias.infotel.dto.response;


import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ARSessionResponse {
    private String sessionId;
    private String userId;
    private boolean cameraEnabled;
    private String deviceType;
    private String resolution;
    private String status; // ACTIVE, PAUSED, ENDED
    private Date createdAt;
    private Date lastUpdated;
    private Date endedAt;
    private Long currentClothingId;
    private String currentClothingName;
    private String processedImageUrl;
    private Map<String, Object> calibrationData;
}