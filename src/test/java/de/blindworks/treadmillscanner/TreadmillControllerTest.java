package de.blindworks.treadmillscanner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TreadmillControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void postWithoutApiKeyIsUnauthorized() throws Exception {
    mockMvc
        .perform(
            post("/api/treadmill/samples")
                .contentType(MediaType.APPLICATION_JSON)
                .content(samplePayload()))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void postWithApiKeyAndValidBodyIsAccepted() throws Exception {
    mockMvc
        .perform(
            post("/api/treadmill/samples")
                .header("X-API-Key", "test-key")
                .contentType(MediaType.APPLICATION_JSON)
                .content(samplePayload()))
        .andExpect(status().isAccepted());
  }

  @Test
  void postWithMissingDeviceIdReturnsBadRequest() throws Exception {
    Map<String, Object> payload = Map.of("sessionId", "session-1");
    mockMvc
        .perform(
            post("/api/treadmill/samples")
                .header("X-API-Key", "test-key")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
        .andExpect(status().isBadRequest());
  }

  private String samplePayload() throws Exception {
    Map<String, Object> payload =
        Map.of(
            "deviceId",
            "REEBOK_1020",
            "sessionId",
            "2026-01-14T10:00:00Z",
            "ts",
            "2026-01-14T10:56:30Z",
            "speedKmh",
            10.0,
            "inclinePct",
            2.0,
            "distanceM",
            490,
            "elapsedS",
            180,
            "totalKcal",
            39,
            "hrBpm",
            0,
            "rawHex",
            "9C05E803EA010014");
    return objectMapper.writeValueAsString(payload);
  }
}
