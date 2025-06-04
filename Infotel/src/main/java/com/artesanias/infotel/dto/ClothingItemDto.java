package com.artesanias.infotel.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClothingItemDto {
    private Long id;
    private String name;
    private String category;
    private String color;
    private String size;
    private String imageUrl;
    private String modelUrl; // URL del modelo 3D para AR
    private BigDecimal price;
    private boolean arCompatible;
    private List<String> tags;
    private ARMetadata arMetadata;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ARMetadata {
        private String textureUrl;
        private String normalMapUrl;
        private Double scale;
        private String[] anchorPoints; // Puntos de anclaje en el cuerpo
        private String clothingType; // "upper", "lower", "full", "accessory"
    }
}