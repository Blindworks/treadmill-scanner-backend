package de.blindworks.treadmillscanner.dto;

import java.time.Instant;

public record SessionSummaryResponse(
    String sessionId,
    Instant startTs,
    Instant endTs,
    long sampleCount) {}
