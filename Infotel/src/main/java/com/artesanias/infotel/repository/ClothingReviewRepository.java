/*package com.artesanias.infotel.repository;

import com.artesanias.infotel.model.ClothingReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingReviewRepository extends JpaRepository<ClothingReview, Long> {

    List<ClothingReview> findByClothingItemIdAndIsApprovedTrue(Long clothingItemId);
    Page<ClothingReview> findByClothingItemIdAndIsApprovedTrue(Long clothingItemId, Pageable pageable);
    List<ClothingReview> findByClothingItemIdAndRating(Long clothingItemId, Integer rating);
    List<ClothingReview> findByUserId(String userId);
    List<ClothingReview> findByVerifiedPurchaseTrue();

    @Query("SELECT AVG(r.rating) FROM ClothingReview r WHERE r.clothingItem.id = :clothingItemId AND r.isApproved = true")
    Double getAverageRatingByClothingItem(@Param("clothingItemId") Long clothingItemId);

    @Query("SELECT COUNT(r) FROM ClothingReview r WHERE r.clothingItem.id = :clothingItemId AND r.isApproved = true")
    Long getReviewCountByClothingItem(@Param("clothingItemId") Long clothingItemId);

    @Query("SELECT r.rating, COUNT(r) FROM ClothingReview r WHERE r.clothingItem.id = :clothingItemId AND r.isApproved = true GROUP BY r.rating ORDER BY r.rating DESC")
    List<Object[]> getRatingDistributionByClothingItem(@Param("clothingItemId") Long clothingItemId);

    @Query("SELECT AVG(r.fitRating) FROM ClothingReview r WHERE r.clothingItem.id = :clothingItemId AND r.fitRating IS NOT NULL AND r.isApproved = true")
    Double getAverageFitRatingByClothingItem(@Param("clothingItemId") Long clothingItemId);

    @Query("SELECT AVG(r.qualityRating) FROM ClothingReview r WHERE r.clothingItem.id = :clothingItemId AND r.qualityRating IS NOT NULL AND r.isApproved = true")
    Double getAverageQualityRatingByClothingItem(@Param("clothingItemId") Long clothingItemId);

    @Query("SELECT AVG(r.valueRating) FROM ClothingReview r WHERE r.clothingItem.id = :clothingItemId AND r.valueRating IS NOT NULL AND r.isApproved = true")
    Double getAverageValueRatingByClothingItem(@Param("clothingItemId") Long clothingItemId);
}*/