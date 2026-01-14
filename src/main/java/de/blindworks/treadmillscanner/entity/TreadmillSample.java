package de.blindworks.treadmillscanner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(
    name = "treadmill_sample",
    indexes = {
      @Index(name = "idx_treadmill_sample_device_ts", columnList = "device_id, ts"),
      @Index(name = "idx_treadmill_sample_session_ts", columnList = "session_id, ts")
    })
public class TreadmillSample {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "device_id", nullable = false)
  private String deviceId;

  @Column(name = "session_id", nullable = false)
  private String sessionId;

  @Column(name = "ts", nullable = false)
  private Instant ts;

  @Column(name = "speed_kmh")
  private Double speedKmh;

  @Column(name = "incline_pct")
  private Double inclinePct;

  @Column(name = "distance_m")
  private Long distanceM;

  @Column(name = "elapsed_s")
  private Integer elapsedS;

  @Column(name = "total_kcal")
  private Integer totalKcal;

  @Column(name = "hr_bpm")
  private Integer hrBpm;

  @Column(name = "raw_hex", length = 512)
  private String rawHex;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @PrePersist
  void onCreate() {
    if (createdAt == null) {
      createdAt = Instant.now();
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }
}
