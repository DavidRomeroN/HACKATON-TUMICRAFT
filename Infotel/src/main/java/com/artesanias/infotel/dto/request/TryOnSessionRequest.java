package com.artesanias.infotel.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TryOnSessionRequest {

    private Long clothingId;
    private String poseData; // JSON string with pose landmarks
    private Integer duration;
    private String screenshot; // Base64 encoded image
}