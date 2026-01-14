package de.blindworks.treadmillscanner.controller;

import de.blindworks.treadmillscanner.dto.LiveStreamSampleRequest;
import de.blindworks.treadmillscanner.dto.LiveStreamSampleResponse;
import de.blindworks.treadmillscanner.service.LiveStreamSampleService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/live")
public class LiveStreamController {

  private static final int DEFAULT_LIMIT = 200;
  private static final int MAX_LIMIT = 2000;

  private final LiveStreamSampleService service;

  public LiveStreamController(LiveStreamSampleService service) {
    this.service = service;
  }

  @PostMapping("/stream")
  public ResponseEntity<Void> ingestLiveSample(@Valid @RequestBody LiveStreamSampleRequest request) {
    service.ingestSample(request);
    return ResponseEntity.accepted().build();
  }

  @GetMapping("/stream")
  public ResponseEntity<List<LiveStreamSampleResponse>> getLiveStream(
      @RequestParam String deviceId, @RequestParam(required = false) Integer limit) {
    int resolvedLimit = clamp(limit, DEFAULT_LIMIT, MAX_LIMIT);
    return ResponseEntity.ok(service.getLatestSamples(deviceId, resolvedLimit));
  }

  private int clamp(Integer limit, int defaultLimit, int maxLimit) {
    if (limit == null || limit <= 0) {
      return defaultLimit;
    }
    return Math.min(limit, maxLimit);
  }
}
