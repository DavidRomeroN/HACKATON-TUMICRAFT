package com.artesanias.infotel.service;

import com.artesanias.infotel.model.Clothing; // Make sure to import this
import com.artesanias.infotel.model.User;
import com.artesanias.infotel.model.UserPreferences;
import com.artesanias.infotel.repository.UserPreferencesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service // Mark it as a Spring component
@RequiredArgsConstructor // For constructor injection of repository
@Slf4j // For logging
public class UserPreferencesInitializerService {

    private final UserPreferencesRepository preferencesRepository;

    // This method will always run in a NEW transaction,
    // regardless of the calling transaction.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserPreferences createDefaultPreferences(User user) {
        UserPreferences preferences = new UserPreferences();
        preferences.setUser(user);
        preferences.setAvatarType(UserPreferences.AvatarType.BASIC);
        preferences.setAvatarSkinTone("#FDBCB4");
        preferences.setBodyType(UserPreferences.BodyType.AVERAGE);
        preferences.setPreferredSize(Clothing.Size.M); // Use Clothing.Size if your model is defined this way
        preferences.setEnableCameraByDefault(false);
        preferences.setEnablePoseDetection(true);
        preferences.setLanguage("es");

        log.info("Creating default preferences for user: {}", user.getEmail());
        return preferencesRepository.save(preferences); // This save operation will now happen in a separate, writable transaction
    }
}