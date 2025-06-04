package com.artesanias.infotel.repository;

import com.artesanias.infotel.model.User;
import com.artesanias.infotel.model.UserPreferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPreferencesRepository extends JpaRepository<UserPreferences, Long> {

    // Find preferences by user
    Optional<UserPreferences> findByUser(User user);

    // Find preferences by user ID
    Optional<UserPreferences> findByUserId(Long userId);

    // Check if user has preferences
    Boolean existsByUserId(Long userId);

    // Find users with specific avatar type
    @Query("SELECT up FROM UserPreferences up WHERE up.avatarType = :avatarType")
    Iterable<UserPreferences> findByAvatarType(@Param("avatarType") UserPreferences.AvatarType avatarType);

    // Find users with specific body type
    @Query("SELECT up FROM UserPreferences up WHERE up.bodyType = :bodyType")
    Iterable<UserPreferences> findByBodyType(@Param("bodyType") UserPreferences.BodyType bodyType);

    // Find users with camera enabled by default
    @Query("SELECT up FROM UserPreferences up WHERE up.enableCameraByDefault = true")
    Iterable<UserPreferences> findUsersWithCameraEnabled();

    // Get preferences with last selected clothing
    @Query("SELECT up FROM UserPreferences up " +
            "LEFT JOIN FETCH up.user u " +
            "WHERE up.lastSelectedClothingId IS NOT NULL AND up.user.id = :userId")
    Optional<UserPreferences> findPreferencesWithLastClothing(@Param("userId") Long userId);
}
