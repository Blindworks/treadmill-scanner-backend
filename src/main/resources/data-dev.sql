INSERT INTO treadmill_sample (
  device_id,
  session_id,
  ts,
  speed_kmh,
  incline_pct,
  distance_m,
  elapsed_s,
  total_kcal,
  hr_bpm,
  raw_hex,
  created_at
) VALUES
  ('dev-device-01', 'session-001', TIMESTAMP WITH TIME ZONE '2024-08-01T10:00:00Z', 8.5, 1.5, 500, 300, 45, 120, '0xAA01', TIMESTAMP WITH TIME ZONE '2024-08-01T10:00:01Z'),
  ('dev-device-01', 'session-001', TIMESTAMP WITH TIME ZONE '2024-08-01T10:05:00Z', 9.0, 2.0, 1500, 600, 90, 128, '0xAA02', TIMESTAMP WITH TIME ZONE '2024-08-01T10:05:01Z'),
  ('dev-device-02', 'session-002', TIMESTAMP WITH TIME ZONE '2024-08-02T18:20:00Z', 6.2, 0.0, 800, 420, 60, 110, '0xBB01', TIMESTAMP WITH TIME ZONE '2024-08-02T18:20:01Z');
