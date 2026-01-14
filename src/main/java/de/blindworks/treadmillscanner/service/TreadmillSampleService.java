package de.blindworks.treadmillscanner.service;

import de.blindworks.treadmillscanner.dto.SessionSummaryResponse;
import de.blindworks.treadmillscanner.dto.TreadmillSampleRequest;
import de.blindworks.treadmillscanner.dto.TreadmillSampleResponse;
import de.blindworks.treadmillscanner.entity.TreadmillSample;
import de.blindworks.treadmillscanner.repository.TreadmillSampleRepository;
import java.time.Instant;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TreadmillSampleService {

  private static final Logger log = LoggerFactory.getLogger(TreadmillSampleService.class);

  private final TreadmillSampleRepository repository;

  public TreadmillSampleService(TreadmillSampleRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void ingestSample(TreadmillSampleRequest request) {
    Instant ts = request.getTs() != null ? request.getTs() : Instant.now();
    TreadmillSample sample = new TreadmillSample();
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
        "Ingested sample deviceId={} sessionId={} ts={} speedKmh={}",
        request.getDeviceId(),
        request.getSessionId(),
        ts,
        request.getSpeedKmh());
  }

  @Transactional(readOnly = true)
  public List<TreadmillSampleResponse> getSamples(
      String sessionId, Integer limit, Instant from, Instant to) {
    Pageable pageable = PageRequest.of(0, limit, Sort.by("ts").ascending());
    List<TreadmillSample> samples;
    if (from != null && to != null) {
      samples = repository.findBySessionIdAndTsBetweenOrderByTsAsc(sessionId, from, to, pageable);
    } else if (from != null) {
      samples = repository.findBySessionIdAndTsAfterOrderByTsAsc(sessionId, from, pageable);
    } else if (to != null) {
      samples = repository.findBySessionIdAndTsBeforeOrderByTsAsc(sessionId, to, pageable);
    } else {
      samples = repository.findBySessionIdOrderByTsAsc(sessionId, pageable);
    }
    return samples.stream().map(this::toResponse).toList();
  }

  @Transactional(readOnly = true)
  public List<SessionSummaryResponse> getSessionSummaries(String deviceId, Integer limit) {
    Pageable pageable = PageRequest.of(0, limit);
    return repository.findSessionSummaries(deviceId, pageable).stream()
        .map(
            summary ->
                new SessionSummaryResponse(
                    summary.getSessionId(),
                    summary.getStartTs(),
                    summary.getEndTs(),
                    summary.getSampleCount()))
        .toList();
  }

  private TreadmillSampleResponse toResponse(TreadmillSample sample) {
    return new TreadmillSampleResponse(
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
