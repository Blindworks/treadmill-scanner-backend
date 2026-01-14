package de.blindworks.treadmillscanner.dto;

import java.time.Instant;

public class LiveStreamSampleResponse {

  private final Long id;
  private final String deviceId;
  private final String sessionId;
  private final Instant ts;
  private final Double speedKmh;
  private final Double inclinePct;
  private final Long distanceM;
  private final Integer elapsedS;
  private final Integer totalKcal;
  private final Integer hrBpm;
  private final String rawHex;
  private final Instant createdAt;

  public LiveStreamSampleResponse(
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
      Instant createdAt) {
    this.id = id;
    this.deviceId = deviceId;
    this.sessionId = sessionId;
    this.ts = ts;
    this.speedKmh = speedKmh;
    this.inclinePct = inclinePct;
    this.distanceM = distanceM;
    this.elapsedS = elapsedS;
    this.totalKcal = totalKcal;
    this.hrBpm = hrBpm;
    this.rawHex = rawHex;
    this.createdAt = createdAt;
  }

  public Long getId() {
    return id;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public Instant getTs() {
    return ts;
  }

  public Double getSpeedKmh() {
    return speedKmh;
  }

  public Double getInclinePct() {
    return inclinePct;
  }

  public Long getDistanceM() {
    return distanceM;
  }

  public Integer getElapsedS() {
    return elapsedS;
  }

  public Integer getTotalKcal() {
    return totalKcal;
  }

  public Integer getHrBpm() {
    return hrBpm;
  }

  public String getRawHex() {
    return rawHex;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }
}
