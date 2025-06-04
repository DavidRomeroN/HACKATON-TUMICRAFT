package com.artesanias.infotel.dto.response;


import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.ClothingCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClothingResponse {

    private Long id;
    private String name;
    private String description;
    private ClothingCategory category;
    private String imageUrl;
    private String modelUrl;
    private String textureUrl;
    private String color;
    private Clothing.Size size;
    private Boolean isPublic;
    private Double price;
    private LocalDateTime createdAt;
    private UserSummaryResponse uploadedBy;
}