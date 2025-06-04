package com.artesanias.infotel.repository;


import com.artesanias.infotel.model.ClothingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingItemRepository extends JpaRepository<ClothingItem, Long> {

    List<ClothingItem> findByAvailableForARTrue();

    List<ClothingItem> findByCategory(String category);

    List<ClothingItem> findByCategoryAndAvailableForARTrue(String category);

    @Query("SELECT c FROM ClothingItem c WHERE c.availableForAR = true AND " +
            "(LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.category) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.color) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<ClothingItem> searchARCompatibleItems(@Param("searchTerm") String searchTerm);

    @Query("SELECT c FROM ClothingItem c WHERE c.availableForAR = true AND c.clothingType = :clothingType")
    List<ClothingItem> findByClothingTypeAndAvailableForARTrue(@Param("clothingType") String clothingType);

    List<ClothingItem> findByBrandAndAvailableForARTrue(String brand);
}
