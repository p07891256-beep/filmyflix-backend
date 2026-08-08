# FilmyFlix - Backend

Netflix-style movie web app backend built with Spring Boot.

## Setup

1. Create MySQL database:
   ```sql
   CREATE DATABASE filmyflix;
   ```
2. Update `src/main/resources/application.properties`:
   - Set your MySQL password 
   - Add your TMDB API key

3. Open this folder in VS Code (with the "Extension Pack for Java" and "Spring Boot Extension Pack" extensions installed), or run from terminal:
   ```bash
   ./mvnw spring-boot:run
   ```
   (Windows: `mvnw.cmd spring-boot:run`)

4. Server runs on `http://localhost:8080`

## Test with Postman

- POST `http://localhost:8080/api/auth/signup` — body: `{"name":"Test","email":"test@test.com","password":"12345"}`
- POST `http://localhost:8080/api/auth/login` — body: `{"email":"test@test.com","password":"12345"}`
- GET `http://localhost:8080/api/movies` — no auth needed

## Folder structure

```
src/main/java/com/filmyflix/
 ├── model/         → Entity classes (User, Profile, Movie, Watchlist, WatchHistory, Review)
 ├── repository/    → Spring Data JPA repositories
 ├── security/      → JWT auth (JwtUtil, JwtAuthFilter, SecurityConfig)
 ├── controller/    → REST API endpoints
 └── dto/           → Request/response objects
```
