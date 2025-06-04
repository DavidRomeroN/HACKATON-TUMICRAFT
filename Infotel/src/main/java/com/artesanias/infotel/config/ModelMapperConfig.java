package com.artesanias.infotel.config;

import com.artesanias.infotel.dto.response.ClothingResponse;
import com.artesanias.infotel.dto.response.PreferencesResponse;
import com.artesanias.infotel.dto.response.UserResponse;
import com.artesanias.infotel.dto.response.UserSummaryResponse;
import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.User;
import com.artesanias.infotel.model.UserPreferences;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Configuración más permisiva para evitar conflictos
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE) // Cambio a LOOSE
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setAmbiguityIgnored(true)
                .setSkipNullEnabled(true);

        // Mapeos simples sin skip problemático

        // User to UserResponse - solo mapeo específico si es necesario
        mapper.createTypeMap(User.class, UserResponse.class);

        // User to UserSummaryResponse - mapeo automático
        mapper.createTypeMap(User.class, UserSummaryResponse.class);

        // Clothing to ClothingResponse
        mapper.createTypeMap(Clothing.class, ClothingResponse.class);

        // UserPreferences to PreferencesResponse
        mapper.createTypeMap(UserPreferences.class, PreferencesResponse.class);

        return mapper;
    }
}