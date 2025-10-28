# Habit Tracker API

A backend service built with Java + Spring Boot + MongoDB that allows users to track habits, log daily completions, and view progress statistics.

## Features
- User registration and JWT authentication
- Create, list (paginated), delete habits
- Log habit entries (complete/undo)
- Dashboard endpoint with current streaks and weekly completion percentage
- Dockerized app + MongoDB for easy local setup
- Integration tests using Testcontainers

## Requirements
- Java 17
- Maven
- Docker (for docker-compose) or a running MongoDB instance

## Quick start (Docker)
1. Copy `.env.example` to `.env` and set `JWT_SECRET` (>= 32 chars).
2. Build & run:
```bash
docker compose up --build
```
3. API base: http://localhost:8080

## Running locally (without Docker)

1. Ensure MongoDB is running locally or update spring.data.mongodb.uri in application.properties.

2. Set environment variable JWT_SECRET in your system (or in your IDE run configuration).

    - On Windows PowerShell:
   ```powershell
   $env:JWT_SECRET = "your_very_long_secret_here_at_least_32_chars"
   ```
3. Build & run:
    ```bash
   mvn clean package
   mvn spring-boot:run
   ```
## Tests
Integration tests use Testcontainers to spin up a MongoDB instance automatically:
```bash
mvn test
```

## Endpoints (examples)

- POST /api/auth/register — register and receive JWT
- POST /api/auth/login — login and receive JWT
- GET /api/habits?page=0&size=10 — paginated habits for logged-in user
- POST /api/habits — create habit
- DELETE /api/habits/{habitId} — delete habit
- POST /api/habits/{habitId}/entries — add/update entry
- DELETE /api/habits/entries/{entryId} — delete entry
- GET /api/habits/stats/dashboard — dashboard stats

## Security note
For learning and development only. Keep JWT_SECRET safe in production — use secrets manager or environment variables set by orchestration.
