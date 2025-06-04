/*package com.artesanias.infotel.repository;

import com.artesanias.infotel.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
    Optional<Tag> findBySlug(String slug);
    List<Tag> findByTagType(String tagType);
    List<Tag> findByIsActiveTrue();
    List<Tag> findByTagTypeAndIsActiveTrue(String tagType);

    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) AND t.isActive = true")
    List<Tag> searchByName(@Param("searchTerm") String searchTerm);

    @Query("SELECT t FROM Tag t ORDER BY t.usageCount DESC")
    List<Tag> findMostUsedTags();

    @Query("SELECT t FROM Tag t WHERE t.tagType = :tagType ORDER BY t.usageCount DESC")
    List<Tag> findMostUsedTagsByType(@Param("tagType") String tagType);
}
*/