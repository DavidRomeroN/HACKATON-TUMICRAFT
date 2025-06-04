package com.artesanias.infotel.service;


import com.artesanias.infotel.dto.request.LoginRequest;
import com.artesanias.infotel.dto.request.RegisterRequest;
import com.artesanias.infotel.dto.response.AuthResponse;
import com.artesanias.infotel.dto.response.UserResponse;
import com.artesanias.infotel.exception.BadRequestException;
import com.artesanias.infotel.model.User;
import com.artesanias.infotel.model.UserPreferences;
import com.artesanias.infotel.repository.UserRepository;
import com.artesanias.infotel.security.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SignatureException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("Attempting login for user: {}", request.getEmail());

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Generate JWT token
        String token = tokenProvider.generateToken(authentication);

        // Get user details
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        log.info("User {} logged in successfully", request.getEmail());
        return new AuthResponse(token, userResponse);
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Attempting registration for user: {}", request.getEmail());

        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email ya está registrado");
        }

        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        user.setActive(true);

        // Save user
        User savedUser = userRepository.save(user);

        // Create default preferences
        UserPreferences preferences = new UserPreferences();
        preferences.setUser(savedUser);
        preferences.setAvatarType(UserPreferences.AvatarType.BASIC);
        preferences.setBodyType(UserPreferences.BodyType.AVERAGE);
        preferences.setPreferredSize(com.artesanias.infotel.model.Clothing.Size.M);
        preferences.setEnableCameraByDefault(false);
        preferences.setEnablePoseDetection(true);
        preferences.setLanguage("es");

        savedUser.setPreferences(preferences);
        savedUser = userRepository.save(savedUser);

        // Generate token
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String token = tokenProvider.generateToken(authentication);

        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);

        log.info("User {} registered successfully", request.getEmail());
        return new AuthResponse(token, userResponse);
    }

    public boolean validateToken(String token) {
        try {
            tokenProvider.validateToken(token);
            return true;
        } catch (SecurityException ex) { // Captura SecurityException para problemas de firma/malformación
            log.error("Token JWT inválido (seguridad): {}", ex.getMessage());
            return false;
        } catch (ExpiredJwtException ex) {
            log.error("Token JWT expirado: {}", ex.getMessage());
            return false;
        } catch (UnsupportedJwtException ex) {
            log.error("Token JWT no soportado: {}", ex.getMessage());
            return false;
        } catch (IllegalArgumentException ex) {
            log.error("Cadena de JWT vacía o nula: {}", ex.getMessage());
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return tokenProvider.getUsernameFromToken(token);
    }
}

