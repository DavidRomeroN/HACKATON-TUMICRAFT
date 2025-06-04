package com.artesanias.infotel.repository;



import com.artesanias.infotel.model.Clothing;
import com.artesanias.infotel.model.TryOnSession;
import com.artesanias.infotel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TryOnSessionRepository extends JpaRepository<TryOnSession, Long> {

    // Find sessions by user
    Page<TryOnSession> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    // Find sessions by clothing
    Page<TryOnSession> findByClothingOrderByCreatedAtDesc(Clothing clothing, Pageable pageable);

    // Find sessions with screenshots
    Page<TryOnSession> findByScreenshotUrlIsNotNullOrderByCreatedAtDesc(Pageable pageable);

    // Find recent sessions
    Page<TryOnSession> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime after, Pageable pageable);

    // Find user sessions with screenshots
    @Query("SELECT ts FROM TryOnSession ts WHERE ts.user = :user AND ts.screenshotUrl IS NOT NULL ORDER BY ts.createdAt DESC")
    Page<TryOnSession> findUserSessionsWithScreenshots(@Param("user") User user, Pageable pageable);

    // Count sessions by user
    Long countByUser(User user);

    // Count sessions by clothing
    Long countByClothing(Clothing clothing);

    // Find sessions in date range
    @Query("SELECT ts FROM TryOnSession ts WHERE ts.createdAt BETWEEN :startDate AND :endDate ORDER BY ts.createdAt DESC")
    Page<TryOnSession> findSessionsInDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    // Get session statistics
    @Query("SELECT " +
            "COUNT(ts) as totalSessions, " +
            "AVG(ts.duration) as avgDuration, " +
            "COUNT(CASE WHEN ts.screenshotUrl IS NOT NULL THEN 1 END) as sessionsWithScreenshots " +
            "FROM TryOnSession ts")
    Object[] getSessionStatistics();

    // Find most used clothing
    @Query("SELECT ts.clothing, COUNT(ts) as usageCount " +
            "FROM TryOnSession ts " +
            "GROUP BY ts.clothing " +
            "ORDER BY usageCount DESC")
    List<Object[]> findMostUsedClothing(Pageable pageable);

    // Find user's favorite clothing (most used)
    @Query("SELECT ts.clothing, COUNT(ts) as usageCount " +
            "FROM TryOnSession ts " +
            "WHERE ts.user = :user " +
            "GROUP BY ts.clothing " +
            "ORDER BY usageCount DESC")
    List<Object[]> findUserFavoriteClothing(@Param("user") User user, Pageable pageable);

    // Average session duration by user
    @Query("SELECT AVG(ts.duration) FROM TryOnSession ts WHERE ts.user = :user")
    Double getAverageSessionDurationByUser(@Param("user") User user);

    // Delete old sessions (for cleanup)
    @Query("DELETE FROM TryOnSession ts WHERE ts.createdAt < :cutoffDate")
    void deleteSessionsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}