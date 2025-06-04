package com.artesanias.infotel.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tryon_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TryOnSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_data", columnDefinition = "TEXT")
    private String sessionData; // JSON with pose data, clothing combinations, etc.

    @Column(name = "screenshot_url")
    private String screenshotUrl;

    private Integer duration; // Session duration in seconds

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothing_id")
    private Clothing clothing;
}