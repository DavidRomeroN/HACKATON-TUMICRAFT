/*package com.artesanias.infotel.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "clothing_tags")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 100)
    private String slug;

    @Column(length = 255)
    private String description;

    @Column(name = "tag_type", length = 50)
    private String tagType; // "style", "material", "occasion", "feature"

    @Column(length = 7)
    private String colorCode; // Para tags de color

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "usage_count")
    private Long usageCount = 0L;

    @ManyToMany(mappedBy = "tags")
    private Set<ClothingItem> clothingItems;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        if (slug == null && name != null) {
            slug = name.toLowerCase().replaceAll("[^a-z0-9]+", "-");
        }
    }

}*/


