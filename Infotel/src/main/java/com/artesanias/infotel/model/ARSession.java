package com.artesanias.infotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "ar_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ARSession {

    @Id
    private String sessionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "device_type")
    private String deviceType;

    private String resolution;

    @Column(name = "camera_enabled")
    private boolean cameraEnabled;

    private String status; // ACTIVE, PAUSED, ENDED

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    @Column(name = "ended_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endedAt;

    @Column(name = "current_clothing_id")
    private Long currentClothingId;

    @Column(name = "calibration_data")
    @Lob
    private String calibrationData;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        lastUpdated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = new Date();
    }
}