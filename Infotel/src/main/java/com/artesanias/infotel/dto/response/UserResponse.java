package com.artesanias.infotel.dto.response;


import com.artesanias.infotel.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private User.Gender gender;
    private String profilePicture;
    private LocalDateTime createdAt;
    private PreferencesResponse preferences;
}
