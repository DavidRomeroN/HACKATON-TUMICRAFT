/*package com.artesanias.infotel.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clothing_collections")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothingCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 100)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(name = "banner_image_url", length = 500)
    private String bannerImageUrl;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(length = 50)
    private String season;

    @Column(length = 4)
    private String year;

    @Column(name = "launch_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date launchDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY)
    private List<ClothingItem> clothingItems;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        if (slug == null && name != null) {
            slug = name.toLowerCase().replaceAll("[^a-z0-9]+", "-");
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}*/
