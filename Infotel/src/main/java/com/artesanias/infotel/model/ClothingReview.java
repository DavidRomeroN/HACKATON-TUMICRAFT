/*package com.artesanias.infotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity
@Table(name = "clothing_reviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothingReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothing_item_id", nullable = false)
    @JsonBackReference
    private ClothingItem clothingItem;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(nullable = false)
    private Integer rating; // 1-5 estrellas

    @Column(length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "verified_purchase")
    private Boolean verifiedPurchase = false;

    @Column(name = "size_purchased", length = 10)
    private String sizePurchased;

    @Column(name = "fit_rating")
    private Integer fitRating; // 1-5 (muy pequeño - muy grande)

    @Column(name = "quality_rating")
    private Integer qualityRating; // 1-5

    @Column(name = "value_rating")
    private Integer valueRating; // 1-5 (relación calidad-precio)

    @Column(name = "helpful_votes")
    private Integer helpfulVotes = 0;

    @Column(name = "total_votes")
    private Integer totalVotes = 0;

    @Column(name = "is_approved")
    private Boolean isApproved = false;

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
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public Double getHelpfulnessRatio() {
        return totalVotes != null && totalVotes > 0 && helpfulVotes != null ?
                (double) helpfulVotes / totalVotes : 0.0;
    }
}*/
