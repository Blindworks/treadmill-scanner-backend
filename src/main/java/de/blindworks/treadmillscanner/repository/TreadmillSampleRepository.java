package de.blindworks.treadmillscanner.repository;

import de.blindworks.treadmillscanner.entity.TreadmillSample;
import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TreadmillSampleRepository extends JpaRepository<TreadmillSample, Long> {

  List<TreadmillSample> findBySessionIdAndTsBetweenOrderByTsAsc(
      String sessionId, Instant from, Instant to, Pageable pageable);

  List<TreadmillSample> findBySessionIdAndTsAfterOrderByTsAsc(
      String sessionId, Instant from, Pageable pageable);

  List<TreadmillSample> findBySessionIdAndTsBeforeOrderByTsAsc(
      String sessionId, Instant to, Pageable pageable);

  List<TreadmillSample> findBySessionIdOrderByTsAsc(String sessionId, Pageable pageable);

  @Query(
      "select s.sessionId as sessionId, min(s.ts) as startTs, max(s.ts) as endTs, count(s) as sampleCount "
          + "from TreadmillSample s "
          + "where s.deviceId = :deviceId "
          + "group by s.sessionId "
          + "order by max(s.ts) desc")
  List<SessionSummaryProjection> findSessionSummaries(
      @Param("deviceId") String deviceId, Pageable pageable);

  interface SessionSummaryProjection {
    String getSessionId();

    Instant getStartTs();

    Instant getEndTs();

    long getSampleCount();
  }
}
