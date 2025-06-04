/*package com.artesanias.infotel.repository;

import com.artesanias.infotel.model.ClothingImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingImageRepository extends JpaRepository<ClothingImage, Long> {

    List<ClothingImage> findByClothingItemIdOrderByDisplayOrder(Long clothingItemId);
    List<ClothingImage> findByClothingItemIdAndImageType(Long clothingItemId, String imageType);
    Optional<ClothingImage> findByClothingItemIdAndIsPrimaryTrue(Long clothingItemId);

    @Query("SELECT i FROM ClothingImage i WHERE i.clothingItem.id = :clothingItemId ORDER BY i.isPrimary DESC, i.displayOrder ASC")
    List<ClothingImage> findByClothingItemOrderedByPrimaryAndDisplay(@Param("clothingItemId") Long clothingItemId);

    List<ClothingImage> findByImageType(String imageType);
}*/