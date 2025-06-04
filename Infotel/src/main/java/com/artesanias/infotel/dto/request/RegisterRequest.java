package com.artesanias.infotel.dto.request;

import com.artesanias.infotel.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Nombre es requerido")
    @Size(min = 2, max = 50, message = "Nombre debe tener entre 2 y 50 caracteres")
    private String name;

    @NotBlank(message = "Email es requerido")
    @Email(message = "Email debe tener un formato válido")
    private String email;

    @NotBlank(message = "Contraseña es requerida")
    @Size(min = 6, max = 100, message = "Contraseña debe tener entre 6 y 100 caracteres")
    private String password;

    private User.Gender gender = User.Gender.OTHER;
}