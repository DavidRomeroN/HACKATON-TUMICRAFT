/*package com.artesanias.infotel.repository;

import com.artesanias.infotel.model.ClothingDimension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingDimensionRepository extends JpaRepository<ClothingDimension, Long> {

    List<ClothingDimension> findByClothingItemId(Long clothingItemId);
    Optional<ClothingDimension> findByClothingItemIdAndSize(Long clothingItemId, String size);

    @Query("SELECT DISTINCT d.size FROM ClothingDimension d WHERE d.clothingItem.id = :clothingItemId ORDER BY d.size")
    List<String> findAvailableSizesByClothingItem(@Param("clothingItemId") Long clothingItemId);

    @Query("SELECT d FROM ClothingDimension d WHERE d.clothingItem.id = :clothingItemId ORDER BY " +
            "CASE d.size " +
            "WHEN 'XS' THEN 1 " +
            "WHEN 'S' THEN 2 " +
            "WHEN 'M' THEN 3 " +
            "WHEN 'L' THEN 4 " +
            "WHEN 'XL' THEN 5 " +
            "WHEN 'XXL' THEN 6 " +
            "ELSE 7 END")
    List<ClothingDimension> findByClothingItemOrderedBySize(@Param("clothingItemId") Long clothingItemId);
}*/