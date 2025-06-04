package com.artesanias.infotel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TryOnSessionResponse {

    private Long id;
    private String screenshotUrl;
    private Integer duration;
    private LocalDateTime createdAt;
    private ClothingResponse clothing;
}