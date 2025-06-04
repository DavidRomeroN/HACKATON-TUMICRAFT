/*package com.artesanias.infotel.repository;

import com.artesanias.infotel.model.ClothingCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothingCollectionRepository extends JpaRepository<ClothingCollection, Long> {

    Optional<ClothingCollection> findBySlug(String slug);
    List<ClothingCollection> findByIsActiveTrue();
    List<ClothingCollection> findByIsFeaturedTrueAndIsActiveTrue();
    List<ClothingCollection> findBySeasonAndIsActiveTrue(String season);
    List<ClothingCollection> findByYearAndIsActiveTrue(String year);

    @Query("SELECT c FROM ClothingCollection c WHERE c.isActive = true ORDER BY c.displayOrder ASC, c.name ASC")
    List<ClothingCollection> findActiveCollectionsOrdered();

    @Query("SELECT c FROM ClothingCollection c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND c.isActive = true")
    List<ClothingCollection> searchByName(@Param("searchTerm") String searchTerm);
}
*/