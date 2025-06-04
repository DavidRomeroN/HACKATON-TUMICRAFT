package com.artesanias.infotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Avatar Preferences
    @Enumerated(EnumType.STRING)
    private AvatarType avatarType = AvatarType.BASIC;

    private String avatarSkinTone = "#FDBCB4";

    @Enumerated(EnumType.STRING)
    private BodyType bodyType = BodyType.AVERAGE;

    private Integer height; // in cm

    private Integer weight; // in kg

    // Clothing Preferences
    @Enumerated(EnumType.STRING)
    private Clothing.Size preferredSize = Clothing.Size.M;

    private String preferredColors; // JSON array of color preferences

    private String favoriteCategories; // JSON array of favorite categories

    // App Preferences
    private Boolean enableCameraByDefault = false;

    private Boolean enablePoseDetection = true;

    private String language = "es";

    @Column(name = "last_selected_clothing_id")
    private Long lastSelectedClothingId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum AvatarType {
        BASIC, REALISTIC, CARTOON
    }

    public enum BodyType {
        SLIM, AVERAGE, ATHLETIC, CURVY, PLUS_SIZE
    }
}