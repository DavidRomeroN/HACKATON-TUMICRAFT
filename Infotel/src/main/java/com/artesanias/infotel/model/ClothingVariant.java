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
@Table(name = "clothing_variants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothingVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothing_item_id", nullable = false)
    @JsonBackReference
    private ClothingItem clothingItem;

    @Column(length = 10)
    private String size;

    @Column(length = 50)
    private String color;

    @Column(length = 100)
    private String colorHex; // Código hexadecimal del color

    @Column(length = 20)
    private String sku; // Stock Keeping Unit

    @Column(length = 20)
    private String barcode;

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 0;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity = 0;

    @Column(name = "price_adjustment", precision = 10, scale = 2)
    private BigDecimal priceAdjustment = BigDecimal.ZERO; // Ajuste de precio para esta variante

    @Column(name = "weight_grams")
    private Integer weightGrams;

    @Column(name = "variant_image_url", length = 500)
    private String variantImageUrl;

    @Column(name = "variant_model_url", length = 500)
    private String variantModelUrl;

    @Column(name = "is_active")
    private Boolean isActive = true;

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

    public Integer getAvailableQuantity() {
        return stockQuantity != null && reservedQuantity != null ?
                Math.max(0, stockQuantity - reservedQuantity) :
                (stockQuantity != null ? stockQuantity : 0);
    }

    public boolean isInStock() {
        return getAvailableQuantity() > 0;
    }
}*/
