package com.artesanias.infotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "clothing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clothing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClothingCategory category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "model_url")
    private String modelUrl; // .glb file URL

    @Column(name = "texture_url")
    private String textureUrl;

    private String color;

    @Enumerated(EnumType.STRING)
    private Size size = Size.M;

    @Column(nullable = false)
    private Boolean isPublic = true;

    @Column(nullable = false)
    private Boolean isActive = true;

    private Double price;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Who uploaded this clothing

    public enum Size {
        XS, S, M, L, XL, XXL
    }
}
