# Treadmill Scanner Backend

Spring Boot (Java 21) backend that ingests treadmill samples via HTTP POST and provides read APIs.

## Local development

### Prerequisites

- Java 21
- Maven 3.9+
- PostgreSQL 16+

### Run with Docker Compose

```bash
docker-compose up --build
```

The API listens on `http://localhost:8080` and expects an API key in `X-API-Key`.

### Run locally without Docker

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/treadmill
export SPRING_DATASOURCE_USERNAME=treadmill
export SPRING_DATASOURCE_PASSWORD=treadmill
export TREADMILL_API_KEY=local-dev-key
mvn spring-boot:run
```

## API usage

### Ingest a sample

```bash
curl -X POST http://localhost:8080/api/treadmill/samples \
  -H "Content-Type: application/json" \
  -H "X-API-Key: local-dev-key" \
  -d '{
    "deviceId": "REEBOK_1020",
    "sessionId": "2026-01-14T10:00:00Z",
    "ts": "2026-01-14T10:56:30Z",
    "speedKmh": 10.0,
    "inclinePct": 2.0,
    "distanceM": 490,
    "elapsedS": 180,
    "totalKcal": 39,
    "hrBpm": 0,
    "rawHex": "9C05E803EA010014"
  }'
```

### Get samples for a session

```bash
curl "http://localhost:8080/api/treadmill/sessions/2026-01-14T10:00:00Z/samples?limit=500" \
  -H "X-API-Key: local-dev-key"
```

Optional filters:

```bash
curl "http://localhost:8080/api/treadmill/sessions/2026-01-14T10:00:00Z/samples?from=2026-01-14T10:55:00Z&to=2026-01-14T11:00:00Z" \
  -H "X-API-Key: local-dev-key"
```

### Get session summaries for a device

```bash
curl "http://localhost:8080/api/treadmill/devices/REEBOK_1020/sessions?limit=50" \
  -H "X-API-Key: local-dev-key"
```

## Hosting (Railway)

- Create a PostgreSQL database in Railway.
- Set the following environment variables for the service:
  - `SPRING_DATASOURCE_URL`
  - `SPRING_DATASOURCE_USERNAME`
  - `SPRING_DATASOURCE_PASSWORD`
  - `TREADMILL_API_KEY`
- Deploy the Dockerfile or build the JAR and run it with `java -jar`.
