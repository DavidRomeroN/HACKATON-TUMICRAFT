package com.artesanias.infotel.dto.response;


import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.UserPreferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesResponse {

    private Long id;

    // Avatar Preferences
    private UserPreferences.AvatarType avatarType;
    private String avatarSkinTone;
    private UserPreferences.BodyType bodyType;
    private Integer height;
    private Integer weight;

    // Clothing Preferences
    private Clothing.Size preferredSize;
    private List<String> preferredColors;
    private List<String> favoriteCategories;

    // App Preferences
    private Boolean enableCameraByDefault;
    private Boolean enablePoseDetection;
    private String language;

    private Long lastSelectedClothingId;
}