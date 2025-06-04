package com.artesanias.infotel.service;


import com.artesanias.infotel.dto.response.UserResponse;
import com.artesanias.infotel.exception.ResourceNotFoundException;
import com.artesanias.infotel.model.User;
import com.artesanias.infotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                new ArrayList<>()
        );
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return modelMapper.map(user, UserResponse.class);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return modelMapper.map(user, UserResponse.class);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findByActiveTrue(pageable);
        return users.map(user -> modelMapper.map(user, UserResponse.class));
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(String name, Pageable pageable) {
        Page<User> users = userRepository.findByNameContainingIgnoreCase(name, pageable);
        return users.map(user -> modelMapper.map(user, UserResponse.class));
    }

    @Transactional
    public UserResponse updateUser(Long id, User updateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Update fields
        if (updateRequest.getName() != null) {
            user.setName(updateRequest.getName());
        }
        if (updateRequest.getGender() != null) {
            user.setGender(updateRequest.getGender());
        }
        if (updateRequest.getProfilePicture() != null) {
            user.setProfilePicture(updateRequest.getProfilePicture());
        }

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        user.setActive(false);
        userRepository.save(user);
        log.info("User {} deactivated", user.getEmail());
    }

    @Transactional(readOnly = true)
    public Long getTotalActiveUsers() {
        return userRepository.countActiveUsers();
    }
}