package com.artesanias.infotel.repository;

import com.artesanias.infotel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find by email (for login)
    Optional<User> findByEmail(String email);

    // Check if email exists
    Boolean existsByEmail(String email);

    // Find active users
    Page<User> findByActiveTrue(Pageable pageable);

    // Find users by name (case insensitive)
    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Find users by gender
    Page<User> findByGender(User.Gender gender, Pageable pageable);

    // Find users created after specific date
    Page<User> findByCreatedAtAfter(LocalDateTime date, Pageable pageable);

    // Custom query to find users with preferences
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.preferences WHERE u.active = true")
    Page<User> findActiveUsersWithPreferences(Pageable pageable);

    // Count active users
    @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
    Long countActiveUsers();

    // Find users who uploaded clothing
    @Query("SELECT DISTINCT u FROM User u JOIN u.uploadedClothing c WHERE c.isActive = true")
    Page<User> findUsersWithUploadedClothing(Pageable pageable);
}