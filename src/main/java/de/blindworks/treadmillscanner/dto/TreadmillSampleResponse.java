package de.blindworks.treadmillscanner.dto;

import java.time.Instant;

public record TreadmillSampleResponse(
    Long id,
    String deviceId,
    String sessionId,
    Instant ts,
    Double speedKmh,
    Double inclinePct,
    Long distanceM,
    Integer elapsedS,
    Integer totalKcal,
    Integer hrBpm,
    String rawHex,
    Instant createdAt) {}
