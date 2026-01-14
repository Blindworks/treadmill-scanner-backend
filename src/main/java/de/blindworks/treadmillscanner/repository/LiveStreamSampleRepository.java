package de.blindworks.treadmillscanner.repository;

import de.blindworks.treadmillscanner.entity.LiveStreamSample;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveStreamSampleRepository extends JpaRepository<LiveStreamSample, Long> {

  List<LiveStreamSample> findByDeviceIdOrderByTsDesc(String deviceId, Pageable pageable);

  Optional<LiveStreamSample> findTopByDeviceIdOrderByTsDesc(String deviceId);

  Optional<LiveStreamSample> findTopByOrderByTsDesc();
}
