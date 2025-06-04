/*package com.artesanias.infotel.repository;

import com.artesanias.infotel.model.ClothingVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingVariantRepository extends JpaRepository<ClothingVariant, Long> {

    List<ClothingVariant> findByClothingItemIdAndIsActiveTrue(Long clothingItemId);
    List<ClothingVariant> findByClothingItemId(Long clothingItemId);
    List<ClothingVariant> findBySku(String sku);
    Optional<ClothingVariant> findByBarcode(String barcode);

    @Query("SELECT v FROM ClothingVariant v WHERE v.clothingItem.id = :clothingItemId AND v.size = :size AND v.isActive = true")
    List<ClothingVariant> findByClothingItemAndSize(@Param("clothingItemId") Long clothingItemId, @Param("size") String size);

    @Query("SELECT v FROM ClothingVariant v WHERE v.clothingItem.id = :clothingItemId AND v.color = :color AND v.isActive = true")
    List<ClothingVariant> findByClothingItemAndColor(@Param("clothingItemId") Long clothingItemId, @Param("color") String color);

    @Query("SELECT v FROM ClothingVariant v WHERE v.clothingItem.id = :clothingItemId AND v.size = :size AND v.color = :color AND v.isActive = true")
    Optional<ClothingVariant> findByClothingItemSizeAndColor(@Param("clothingItemId") Long clothingItemId, @Param("size") String size, @Param("color") String color);

    @Query("SELECT v FROM ClothingVariant v WHERE v.stockQuantity - v.reservedQuantity > 0 AND v.isActive = true")
    List<ClothingVariant> findAvailableVariants();

    @Query("SELECT v FROM ClothingVariant v WHERE v.clothingItem.id = :clothingItemId AND v.stockQuantity - v.reservedQuantity > 0 AND v.isActive = true")
    List<ClothingVariant> findAvailableVariantsByClothingItem(@Param("clothingItemId") Long clothingItemId);

    @Query("SELECT DISTINCT v.size FROM ClothingVariant v WHERE v.clothingItem.id = :clothingItemId AND v.isActive = true ORDER BY v.size")
    List<String> findAvailableSizesByClothingItem(@Param("clothingItemId") Long clothingItemId);

    @Query("SELECT DISTINCT v.color FROM ClothingVariant v WHERE v.clothingItem.id = :clothingItemId AND v.isActive = true ORDER BY v.color")
    List<String> findAvailableColorsByClothingItem(@Param("clothingItemId") Long clothingItemId);
}*/