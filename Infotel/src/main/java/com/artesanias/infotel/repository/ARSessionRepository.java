package com.artesanias.infotel.repository;


import com.artesanias.infotel.model.ARSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ARSessionRepository extends JpaRepository<ARSession, String> {

    List<ARSession> findByUserIdAndStatus(String userId, String status);

    @Query("SELECT s FROM ARSession s WHERE s.status = 'ACTIVE' AND s.lastUpdated < :cutoffTime")
    List<ARSession> findInactiveSessions(@Param("cutoffTime") Date cutoffTime);

    Optional<ARSession> findBySessionIdAndStatus(String sessionId, String status);

    long countByStatus(String status);

    @Query("SELECT COUNT(s) FROM ARSession s WHERE s.createdAt >= :startDate")
    long countSessionsCreatedAfter(@Param("startDate") Date startDate);
}