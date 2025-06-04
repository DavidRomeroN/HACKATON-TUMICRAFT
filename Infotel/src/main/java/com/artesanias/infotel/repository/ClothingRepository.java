package com.artesanias.infotel.repository;


import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.ClothingCategory;
import com.artesanias.infotel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Long> {

    // Find by category
    Page<Clothing> findByCategoryAndIsActiveTrue(ClothingCategory category, Pageable pageable);

    // Find public clothing
    Page<Clothing> findByIsPublicTrueAndIsActiveTrue(Pageable pageable);

    // Find by user
    Page<Clothing> findByUserAndIsActiveTrue(User user, Pageable pageable);

    // Find by name (case insensitive)
    Page<Clothing> findByNameContainingIgnoreCaseAndIsActiveTrue(String name, Pageable pageable);

    // Find by color
    Page<Clothing> findByColorContainingIgnoreCaseAndIsActiveTrue(String color, Pageable pageable);

    // Find by size
    Page<Clothing> findBySizeAndIsActiveTrue(Clothing.Size size, Pageable pageable);

    // Find by multiple categories
    Page<Clothing> findByCategoryInAndIsActiveTrue(List<ClothingCategory> categories, Pageable pageable);

    // Find recent clothing
    Page<Clothing> findByIsActiveTrueOrderByCreatedAtDesc(Pageable pageable);

    // Custom query for advanced search
    @Query("SELECT c FROM Clothing c WHERE " +
            "(:category IS NULL OR c.category = :category) AND " +
            "(:size IS NULL OR c.size = :size) AND " +
            "(:color IS NULL OR LOWER(c.color) LIKE LOWER(CONCAT('%', :color, '%'))) AND " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:isPublic IS NULL OR c.isPublic = :isPublic) AND " +
            "c.isActive = true " +
            "ORDER BY c.createdAt DESC")
    Page<Clothing> findClothingByFilters(
            @Param("category") ClothingCategory category,
            @Param("size") Clothing.Size size,
            @Param("color") String color,
            @Param("name") String name,
            @Param("isPublic") Boolean isPublic,
            Pageable pageable
    );

    // Find clothing with 3D models
    @Query("SELECT c FROM Clothing c WHERE c.modelUrl IS NOT NULL AND c.isActive = true")
    Page<Clothing> findClothingWith3DModels(Pageable pageable);

    // Count clothing by category
    @Query("SELECT c.category, COUNT(c) FROM Clothing c WHERE c.isActive = true GROUP BY c.category")
    List<Object[]> countClothingByCategory();

    // Find popular clothing (most used in sessions)
    @Query("SELECT c, COUNT(ts) as usageCount FROM Clothing c " +
            "LEFT JOIN TryOnSession ts ON ts.clothing = c " +
            "WHERE c.isActive = true " +
            "GROUP BY c " +
            "ORDER BY usageCount DESC")
    Page<Object[]> findPopularClothing(Pageable pageable);

    // Find similar clothing by category and color
    @Query("SELECT c FROM Clothing c WHERE " +
            "c.category = :category AND " +
            "c.color = :color AND " +
            "c.id != :excludeId AND " +
            "c.isActive = true " +
            "ORDER BY c.createdAt DESC")
    List<Clothing> findSimilarClothing(
            @Param("category") ClothingCategory category,
            @Param("color") String color,
            @Param("excludeId") Long excludeId,
            Pageable pageable
    );
}
