package de.blindworks.treadmillscanner.controller;

import de.blindworks.treadmillscanner.dto.SessionSummaryResponse;
import de.blindworks.treadmillscanner.dto.TreadmillSampleRequest;
import de.blindworks.treadmillscanner.dto.TreadmillSampleResponse;
import de.blindworks.treadmillscanner.service.TreadmillSampleService;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/treadmill")
public class TreadmillController {

  private static final int DEFAULT_SAMPLES_LIMIT = 500;
  private static final int MAX_SAMPLES_LIMIT = 5000;
  private static final int DEFAULT_SESSION_LIMIT = 50;
  private static final int MAX_SESSION_LIMIT = 50;

  private final TreadmillSampleService service;

  public TreadmillController(TreadmillSampleService service) {
    this.service = service;
  }

  @PostMapping("/samples")
  public ResponseEntity<Void> ingestSample(@Valid @RequestBody TreadmillSampleRequest request) {
    service.ingestSample(request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/sessions/{sessionId}/samples")
  public ResponseEntity<List<TreadmillSampleResponse>> getSessionSamples(
      @PathVariable String sessionId,
      @RequestParam(required = false) Integer limit,
      @RequestParam(required = false) Instant from,
      @RequestParam(required = false) Instant to) {
    int resolvedLimit = clamp(limit, DEFAULT_SAMPLES_LIMIT, MAX_SAMPLES_LIMIT);
    return ResponseEntity.ok(service.getSamples(sessionId, resolvedLimit, from, to));
  }

  @GetMapping("/devices/{deviceId}/sessions")
  public ResponseEntity<List<SessionSummaryResponse>> getDeviceSessions(
      @PathVariable String deviceId, @RequestParam(required = false) Integer limit) {
    int resolvedLimit = clamp(limit, DEFAULT_SESSION_LIMIT, MAX_SESSION_LIMIT);
    return ResponseEntity.ok(service.getSessionSummaries(deviceId, resolvedLimit));
  }

  private int clamp(Integer limit, int defaultLimit, int maxLimit) {
    if (limit == null || limit <= 0) {
      return defaultLimit;
    }
    return Math.min(limit, maxLimit);
  }
}
