# Backend Challenge Course

A RESTful API developed with Java and Spring Boot following Clean Architecture principles.

The system manages user data and integrates with the public [PokéAPI](https://pokeapi.co/) to enrich user profiles with Pokémon details.

---

## Table of Contents

1. [Technologies Used](#technologies-used)
2. [Features](#features)
3. [Prerequisites](#prerequisites)
4. [System Entities](#system-entities)
5. [Request Body Structure](#request-body-structure)
6. [Application Routes (Endpoints)](#application-routes-endpoints)
7. [Standards and Design Patterns](#standards-and-design-patterns)
8. [How to Run the Application](#how-to-run-the-application)
9. [Data Seeding (Empty Database)](#data-seeding-empty-database)
10. [How to Run Tests](#how-to-run-tests)
11. [Deployment Considerations](#deployment-considerations)
12. [Decisions Made](#decisions-made)

---

## Technologies Used

- **Java 17 / 21**
- **Spring Boot 3.x**
  - Spring Web / Spring MVC
  - Spring Data JPA
  - Bean Validation
- **PostgreSQL**
- **Hibernate**
- **Docker and Docker Compose**
- **JUnit 5**
- **Mockito**
- **MockMvc**
- **Maven**
- **SpringDoc OpenAPI / Swagger**
- **PokéAPI**

---

## Features

- **User Registration:** Registers new users while ensuring email uniqueness and associating a list of Pokémon IDs.
- **Enriched User Retrieval:** Fetches user records from the local database and dynamically integrates full Pokémon details through the PokéAPI.
- **Email Validation:** Validates the user email and prevents duplicate registrations.
- **Pokémon Integration:** Retrieves additional Pokémon information from the public PokéAPI.
- **Environment Database Isolation:** Maintains distinct databases for development or production and integration testing:
  - `challenge_db`
  - `challenge_db_test`
- **Clean Architecture:** Organizes the application into independent layers with clearly defined responsibilities.
- **RESTful API:** Exposes HTTP endpoints to create and retrieve users.
- **Integration Test Suite:** Includes controller tests with database cleanup and external REST-call isolation using mocks.
- **Swagger Documentation:** Provides interactive API documentation through SpringDoc OpenAPI.
- **Docker Support:** Allows the application and database to run in containerized environments.

---

## Prerequisites

Before running the application and tests, ensure you have the following installed:

- **JDK 17** or higher.
- **Apache Maven 3.8+**.
  - Maven is optional if you use the Maven Wrapper included in the project.
- **Docker Desktop** with Linux container support.
- **Docker Compose**.
- A database GUI client such as:
  - [DBeaver](https://dbeaver.io/)
  - [pgAdmin](https://www.pgadmin.org/)
  - Another PostgreSQL-compatible client.
- Available ports:
  - `8080` for the Spring Boot application.
  - `5432` for PostgreSQL.
- Internet access if the application needs to retrieve Pokémon details from the public PokéAPI.

> Docker Compose should be available without requiring `sudo` for the current user.

---

## System Entities

### Table: `usuario`

| Column | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | `PRIMARY KEY`, auto-generated | Unique identifier for the user |
| `nombre` | `VARCHAR(255)` | `NOT NULL` | User's full name |
| `edad` | `INTEGER` | `NOT NULL` | User's age |
| `email` | `VARCHAR(255)` | `NOT NULL`, `UNIQUE` | User's email address |
| `pokemon_ids` | `VARCHAR(255)` | Nullable | Serialized list of assigned Pokémon IDs |

> The exact table and column names depend on the JPA entity and database configuration used by the application.

---

## Request Body Structure

When issuing `POST` requests to `/api/usuarios`, requests must be sent as JSON with the following header:

```http
Content-Type: application/json
```

### Example request body

```json
{
  "nombre": "Ricardo",
  "edad": 30,
  "email": "rdl@mail.com",
  "pokemonIds":[40][45]
}
```

### Request fields

| Field | Type | Required | Description |
| :--- | :--- | :--- | :--- |
| `nombre` | `String` | Yes | User's full name |
| `edad` | `Integer` | Yes | User's age |
| `email` | `String` | Yes | User's email address |
| `pokemonIds` | `Array<Integer>` | No | List of Pokémon IDs associated with the user |

> The request uses the field `email`. If the implementation currently expects `correo`, update either the DTO or this documentation so both remain consistent.

### Example using cURL

```bash
curl -X POST "http://localhost:8080/api/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ricardo",
    "edad": 30,
    "email": "rdl@mail.com",
    "pokemonIds":[40][45]
  }'
```

---

## Application Routes (Endpoints)

The following endpoints are available in the application.

### Create a user

```http
POST /api/usuarios
```

Creates a new user.

#### Example request

```json
{
  "nombre": "Ricardo",
  "edad": 30,
  "email": "rdl@mail.com",
  "pokemonIds":[40][45]
}
```

#### Example cURL request

```bash
curl -X POST "http://localhost:8080/api/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ricardo",
    "edad": 30,
    "email": "rdl@mail.com",
    "pokemonIds":[40][45]
  }'
```

---

### Retrieve all users

```http
GET /api/usuarios
```

Returns all users stored in the database.

#### Example cURL request

```bash
curl "http://localhost:8080/api/usuarios"
```

---

### Retrieve a user by ID

```http
GET /api/usuarios/{id}
```

Returns a specific user by ID, including enriched Pokémon information when available.

#### Example cURL request

```bash
curl "http://localhost:8080/api/usuarios/1"
```

---

### Interactive API documentation

When the application is running locally, Swagger UI is available at:

[Open Swagger UI](http://localhost:8080/swagger-ui/index.html)

OpenAPI specification:

[OpenAPI JSON](http://localhost:8080/v3/api-docs)

> These links work when the application is running on `localhost` and port `8080`.

---

## Standards and Design Patterns

The application follows the following architectural and development standards:

### Clean Architecture

The project is organized into layers with clearly defined responsibilities:

- **Domain:** Core business entities and rules.
- **Application:** Use cases and business orchestration.
- **Infrastructure:** Database access, external API clients and technical implementations.
- **Presentation:** REST controllers and HTTP-related concerns.

The objective is to keep business logic independent from frameworks, databases and external services.

### REST principles

The API follows REST-oriented conventions:

- Uses HTTP verbs according to the operation.
- Uses resource-oriented URLs.
- Exchanges data using JSON.
- Uses appropriate HTTP status codes.
- Keeps requests stateless.

### DTO pattern

Data Transfer Objects are used to isolate the external API contract from the internal persistence entities.

### Repository pattern

Repositories abstract database access and isolate persistence logic from the rest of the application.

### Service layer

The service layer contains application and business logic, keeping controllers focused on handling HTTP requests and responses.

### Dependency Injection

Spring Dependency Injection is used to provide services, repositories and external clients to the classes that require them.

### External API isolation

PokéAPI integration is isolated behind a dedicated client or service. This prevents external communication details from leaking into the business logic.

### Validation

Request data should be validated before reaching the business layer. Invalid input should produce an appropriate client error response.

---

## How to Run the Application

### 1. Clone the repository

Replace `<YOUR_REPOSITORY_URL>` with the actual repository URL:

```bash
git clone <YOUR_REPOSITORY_URL>
cd challenge-ejercicio
```

> Make sure the directory name matches the actual repository directory.

### 2. Build the application

Using Maven:

```bash
mvn clean package
```

Or, using the Maven Wrapper:

```bash
./mvnw clean package
```

On Windows:

```bash
mvnw.cmd clean package
```

### 3. Start the database and application with Docker Compose

```bash
docker compose up --build -d
```

If the project uses the legacy Docker Compose command, use:

```bash
docker-compose up --build -d
```

### 4. Check the container status

```bash
docker compose ps
```

Or:

```bash
docker-compose ps
```

### 5. View the backend logs

Replace `challenge-backend` with the actual container name if it differs:

```bash
docker logs -f challenge-backend
```

You can also list the running containers with:

```bash
docker ps
```

### 6. Test the application

Once the application is running, open:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)

Or test the API directly:

```bash
curl "http://localhost:8080/api/usuarios"
```

### 7. Stop the services

```bash
docker compose down
```

Or:

```bash
docker-compose down
```

To stop the services and remove their volumes:

```bash
docker compose down -v
```

> Use `-v` carefully because it removes the database volume and may delete persisted local data.

---

## Data Seeding (Empty Database)

If the database starts empty, users can be created by sending a `POST` request to:

```http
POST /api/usuarios
```

### Example seed request

```bash
curl -X POST "http://localhost:8080/api/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ricardo",
    "edad": 30,
    "email": "rdl@mail.com",
    "pokemonIds":[40][45]
  }'
```

### Seeding through Swagger

1. Start the application.
2. Open [Swagger UI](http://localhost:8080/swagger-ui/index.html).
3. Locate `POST /api/usuarios`.
4. Select **Try it out**.
5. Enter the JSON request body.
6. Select **Execute**.

### Database configuration

The development or production database is identified as:

```text
challenge_db
```

The integration-test database is identified as:

```text
challenge_db_test
```

The actual database names, usernames, passwords and ports should be configured through environment variables or the project configuration files.

Do not commit real passwords or secrets to the repository.

---

## How to Run Tests

### Run all tests with Maven

```bash
mvn test
```

Or with the Maven Wrapper:

```bash
./mvnw test
```

On Windows:

```bash
mvnw.cmd test
```

### Run a clean test build

```bash
mvn clean verify
```

Or:

```bash
./mvnw clean verify
```

### Testing technologies

The project uses:

- **JUnit 5** for test organization and assertions.
- **Mockito** for mocking dependencies.
- **MockMvc** for testing HTTP endpoints.
- **Spring Boot Test** for application-context and integration tests.
- **Test database isolation** to prevent test data from affecting development data.
- **Mocked external API calls** to avoid depending on PokéAPI during automated tests.

### Test database

Integration tests should use:

```text
challenge_db_test
```

The test environment must be isolated from the development or production database.

### Example test command with a profile

If the project defines a test profile, it can be executed with:

```bash
mvn test -Dspring.profiles.active=test
```

> The exact command depends on the profiles configured in the project.

---

## Deployment Considerations

Before deploying the application to another environment, consider the following:

### Environment variables

Configure environment-specific values outside the source code:

- Database URL.
- Database username.
- Database password.
- Active Spring profile.
- External API configuration.
- Server port.
- Logging level.

### Database migrations

For production environments, prefer a migration tool such as:

- Flyway.
- Liquibase.

Avoid depending exclusively on automatic schema generation for production databases.

### Database persistence

Ensure that PostgreSQL uses a persistent volume in Docker or an external managed database.

### Secrets

Do not commit any of the following to the repository:

- Database passwords.
- API keys.
- Access tokens.
- Private credentials.
- Production connection strings.

Use environment variables or a secret-management service instead.

### External API availability

The application depends on PokéAPI for Pokémon enrichment. Consider:

- Connection timeouts.
- Error handling.
- Retry policies.
- Rate limits.
- Caching.
- A fallback response when PokéAPI is unavailable.

### Health checks

Configure health checks for:

- The Spring Boot application.
- The PostgreSQL database.
- External dependencies when appropriate.

### Logging and monitoring

In production, configure:

- Structured logs.
- Centralized log collection.
- Error tracking.
- Metrics.
- Application health monitoring.

### Container configuration

Use production-appropriate Docker images and configurations.

The application container should not contain development credentials or unnecessary tools.

### HTTPS and reverse proxy

In a public deployment, expose the API through HTTPS and consider using a reverse proxy such as:

- NGINX.
- Traefik.
- A cloud load balancer.

### Resource limits

Define CPU and memory limits for the application and database containers.

### CORS and security

Configure CORS according to the actual frontend domains. Do not allow unrestricted origins in production unless there is a specific reason.

---

## Decisions Made

### Clean Architecture

Clean Architecture was selected to make the system easier to maintain and extend.

The architecture separates business rules from frameworks, databases and external services. This facilitates future modifications and makes the application easier to test.

### JPA, Hibernate and Spring Data JPA

The application uses Hibernate as the ORM implementation through the JPA specification and Spring Data JPA.

#### Why use JPA, Hibernate and Spring Data JPA?

- **Abstraction and productivity:** `JpaRepository` eliminates repetitive CRUD query code and accelerates development.
- **Object-relational mapping:** Hibernate maps Java objects to PostgreSQL tables and handles database data types and relationships.
- **Maintainability and portability:** JPA helps decouple the application from a specific database engine, facilitating future changes and testing.
- **Automatic schema management:** JPA and Hibernate can synchronize the table structure with the entities through the `ddl-auto` configuration.

> For production environments, explicit database migrations with Flyway or Liquibase are generally safer than relying exclusively on automatic schema generation.

### Docker

Docker was selected to make the application and its database environment more portable.

Benefits include:

- Consistent development environments.
- Simplified database setup.
- Easier onboarding for new developers.
- Isolation between services.
- Reproducible local execution.

### Testing framework: JUnit 5

JUnit 5 was selected because it is widely adopted in Java projects and integrates natively with the Spring Boot ecosystem through:

```text
spring-boot-starter-test
```

This reduces the need to configure multiple external testing dependencies manually.

### Mockito and MockMvc

- **Mockito:** Used to isolate components by mocking dependencies.
- **MockMvc:** Used to test Spring MVC controllers without requiring a full external web server.

### PostgreSQL

PostgreSQL was selected as the relational database management system because it provides:

- Strong data consistency.
- Mature SQL support.
- Reliable transaction management.
- Good compatibility with Spring Data JPA.
- Excellent Docker support.

### PokéAPI

PokéAPI was selected as the external data source for enriching user profiles with Pokémon information.

The integration is performed dynamically through an external client or service.

### Maven

Maven was selected for:

- Dependency management.
- Build automation.
- Test execution.
- Packaging.
- Integration with the Spring Boot ecosystem.

---

## Known Considerations

- The API requires PostgreSQL to be available when persistence operations are executed.
- Pokémon enrichment depends on the availability of PokéAPI.
- The exact database schema depends on the JPA entity mappings and configuration.
- The repository URL and Docker container names must be updated before using the commands literally.
- The request field must remain consistent between the DTO, controller, tests and this README. The examples in this document use `email`.
- Port `8080` must be available for the backend.
- Port `5432` must be available for PostgreSQL.
