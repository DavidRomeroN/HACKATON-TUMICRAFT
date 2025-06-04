package com.artesanias.infotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clothing_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothingItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    private String color;
    private String size;
    private String brand;
    private String description;
    private String imageUrl;
    private String modelUrl;
    private String textureUrl;

    private BigDecimal price;

    @Column(nullable = false)
    private Boolean availableForAR = true;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    // Metadatos específicos para AR
    @Column(name = "ar_scale")
    private Double arScale = 1.0;

    @Column(name = "anchor_points")
    private String anchorPoints; // JSON con puntos de anclaje

    @Column(name = "clothing_type")
    private String clothingType; // "upper", "lower", "full", "accessory"

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    @ElementCollection
    @CollectionTable(name = "clothing_tags", joinColumns = @JoinColumn(name = "clothing_id"))
    @Column(name = "tag")
    private List<String> tags;
}


