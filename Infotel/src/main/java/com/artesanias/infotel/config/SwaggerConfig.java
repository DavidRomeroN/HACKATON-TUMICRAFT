package com.artesanias.infotel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Virtual Try-On API",
                version = "1.0.0",
                description = "API REST para aplicación de probador virtual de ropa con avatar 3D",
                contact = @Contact(
                        name = "Virtual Try-On Team",
                        email = "support@virtualtryon.com",
                        url = "https://virtualtryon.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080/api", description = "Servidor de Desarrollo"),
                @Server(url = "https://api.virtualtryon.com/api", description = "Servidor de Producción")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "Autenticación JWT. Formato: 'Bearer {token}'"
)
public class SwaggerConfig {
}
