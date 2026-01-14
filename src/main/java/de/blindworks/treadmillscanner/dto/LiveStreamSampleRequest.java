package de.blindworks.treadmillscanner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public class LiveStreamSampleRequest {

  @NotBlank
  private String deviceId;

  @NotBlank
  private String sessionId;

  private Instant ts;

  private Double speedKmh;

  private Double inclinePct;

  private Long distanceM;

  private Integer elapsedS;

  private Integer totalKcal;

  private Integer hrBpm;

  @Size(max = 512)
  private String rawHex;

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Instant getTs() {
    return ts;
  }

  public void setTs(Instant ts) {
    this.ts = ts;
  }

  public Double getSpeedKmh() {
    return speedKmh;
  }

  public void setSpeedKmh(Double speedKmh) {
    this.speedKmh = speedKmh;
  }

  public Double getInclinePct() {
    return inclinePct;
  }

  public void setInclinePct(Double inclinePct) {
    this.inclinePct = inclinePct;
  }

  public Long getDistanceM() {
    return distanceM;
  }

  public void setDistanceM(Long distanceM) {
    this.distanceM = distanceM;
  }

  public Integer getElapsedS() {
    return elapsedS;
  }

  public void setElapsedS(Integer elapsedS) {
    this.elapsedS = elapsedS;
  }

  public Integer getTotalKcal() {
    return totalKcal;
  }

  public void setTotalKcal(Integer totalKcal) {
    this.totalKcal = totalKcal;
  }

  public Integer getHrBpm() {
    return hrBpm;
  }

  public void setHrBpm(Integer hrBpm) {
    this.hrBpm = hrBpm;
  }

  public String getRawHex() {
    return rawHex;
  }

  public void setRawHex(String rawHex) {
    this.rawHex = rawHex;
  }
}
