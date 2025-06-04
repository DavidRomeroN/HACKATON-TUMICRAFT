package com.artesanias.infotel.dto.request;


import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.ClothingCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClothingUploadRequest {

    @NotBlank(message = "Nombre es requerido")
    private String name;

    private String description;

    @NotNull(message = "Categoría es requerida")
    private ClothingCategory category;

    private String color;

    private Clothing.Size size = Clothing.Size.M;

    private Boolean isPublic = true;

    private Double price;
}
