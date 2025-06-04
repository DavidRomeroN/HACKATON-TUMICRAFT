/*package com.artesanias.infotel.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "clothing_dimensions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothingDimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothing_item_id", nullable = false)
    @JsonBackReference
    private ClothingItem clothingItem;

    @Column(length = 10)
    private String size;

    @Column(name = "chest_cm", precision = 5, scale = 2)
    private BigDecimal chestCm;

    @Column(name = "waist_cm", precision = 5, scale = 2)
    private BigDecimal waistCm;

    @Column(name = "hip_cm", precision = 5, scale = 2)
    private BigDecimal hipCm;

    @Column(name = "length_cm", precision = 5, scale = 2)
    private BigDecimal lengthCm;

    @Column(name = "shoulder_cm", precision = 5, scale = 2)
    private BigDecimal shoulderCm;

    @Column(name = "sleeve_cm", precision = 5, scale = 2)
    private BigDecimal sleeveCm;

    @Column(name = "inseam_cm", precision = 5, scale = 2)
    private BigDecimal inseamCm;

    @Column(name = "neck_cm", precision = 5, scale = 2)
    private BigDecimal neckCm;

    @Column(name = "arm_hole_cm", precision = 5, scale = 2)
    private BigDecimal armHoleCm;

    @Column(name = "thigh_cm", precision = 5, scale = 2)
    private BigDecimal thighCm;

    @Column(name = "measurement_unit", length = 10)
    private String measurementUnit = "cm";

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}*/