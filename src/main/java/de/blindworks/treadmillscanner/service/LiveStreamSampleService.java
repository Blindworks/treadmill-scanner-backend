package de.blindworks.treadmillscanner.service;

import de.blindworks.treadmillscanner.dto.LiveStreamSampleRequest;
import de.blindworks.treadmillscanner.dto.LiveStreamSampleResponse;
import de.blindworks.treadmillscanner.entity.LiveStreamSample;
import de.blindworks.treadmillscanner.repository.LiveStreamSampleRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LiveStreamSampleService {

  private static final Logger log = LoggerFactory.getLogger(LiveStreamSampleService.class);

  private final LiveStreamSampleRepository repository;

  public LiveStreamSampleService(LiveStreamSampleRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void ingestSample(LiveStreamSampleRequest request) {
    Instant ts = request.getTs() != null ? request.getTs() : Instant.now();
    LiveStreamSample sample = new LiveStreamSample();
    sample.setDeviceId(request.getDeviceId());
    sample.setSessionId(request.getSessionId());
    sample.setTs(ts);
    sample.setSpeedKmh(request.getSpeedKmh());
    sample.setInclinePct(request.getInclinePct());
    sample.setDistanceM(request.getDistanceM());
    sample.setElapsedS(request.getElapsedS());
    sample.setTotalKcal(request.getTotalKcal());
    sample.setHrBpm(request.getHrBpm());
    sample.setRawHex(request.getRawHex());
    repository.save(sample);

    log.info(
        "Ingested live stream sample deviceId={} sessionId={} ts={} speedKmh={}",
        request.getDeviceId(),
        request.getSessionId(),
        ts,
        request.getSpeedKmh());
  }

  @Transactional(readOnly = true)
  public List<LiveStreamSampleResponse> getLatestSamples(String deviceId, int limit) {
    Pageable pageable = PageRequest.of(0, limit);
    return repository.findByDeviceIdOrderByTsDesc(deviceId, pageable).stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional(readOnly = true)
  public Optional<LiveStreamSampleResponse> getLatestSample(String deviceId) {
    Optional<LiveStreamSample> sample =
        deviceId == null
            ? repository.findTopByOrderByTsDesc()
            : repository.findTopByDeviceIdOrderByTsDesc(deviceId);
    return sample.map(this::toResponse);
  }

  private LiveStreamSampleResponse toResponse(LiveStreamSample sample) {
    return new LiveStreamSampleResponse(
        sample.getId(),
        sample.getDeviceId(),
        sample.getSessionId(),
        sample.getTs(),
        sample.getSpeedKmh(),
        sample.getInclinePct(),
        sample.getDistanceM(),
        sample.getElapsedS(),
        sample.getTotalKcal(),
        sample.getHrBpm(),
        sample.getRawHex(),
        sample.getCreatedAt());
  }
}
