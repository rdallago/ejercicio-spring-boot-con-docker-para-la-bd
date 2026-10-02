# Backend Challenge Course

A RESTful API developed with Java and Spring Boot following Clean Architecture principles and secured with HTTPS.

The application manages user data and integrates with the public [PokéAPI](https://pokeapi.co/) to enrich user profiles with Pokémon information.

---

## Table of Contents

1. [Technologies Used](#technologies-used)
2. [Features](#features)
3. [Prerequisites](#prerequisites)
4. [System Entities](#system-entities)
5. [Request Body Structure](#request-body-structure)
6. [Application Routes (Endpoints)](#application-routes-endpoints)
7. [How to Run the Application](#how-to-run-the-application)
8. [Data Seeding (Empty Database)](#data-seeding-empty-database)
9. [How to Run Tests](#how-to-run-tests)
10. [Deployment Considerations](#deployment-considerations)
11. [Decisions Made](#decisions-made)

---

## Technologies Used

- **Java 17 / 21**
- **Spring Boot 3.x**
  - Spring Web / Spring MVC
  - Spring Data JPA
  - Bean Validation
  - Spring Security
- **HTTPS / TLS** for secure API communication
- **PostgreSQL**
- **Hibernate**
- **Docker and Docker Compose**
- **Maven**
- **JUnit 5**
- **Mockito**
- **MockMvc**
- **SpringDoc OpenAPI / Swagger**
- **PokéAPI**

---

## Features

- **User registration:** Creates users while validating email uniqueness.
- **User CRUD:** Creates, retrieves, updates and deletes users.
- **Pokémon association:** Associates a list of Pokémon IDs with each user.
- **Enriched user retrieval:** Retrieves users from the local database and enriches their Pokémon information through PokéAPI.
- **HTTPS security:** Exposes the application API through secure HTTPS communication.
- **Database isolation:** Uses separate databases for the application and integration tests:
  - `challenge_db`
  - `challenge_db_test`
- **Clean Architecture:** Separates business logic, persistence, external integrations and presentation concerns.
- **REST API:** Provides secure endpoints for user management.
- **Integration testing:** Includes controller integration tests connected to the test database.
- **Swagger documentation:** Provides interactive API documentation through SpringDoc OpenAPI.
- **Docker support:** Allows the application and PostgreSQL database to run in containers.

---

## Prerequisites

Before running the application, make sure the following requirements are met:

- **JDK 17** or higher.
- **Docker Desktop** with Linux container support.
- **Docker Compose**.
- A valid HTTPS/TLS configuration for the application.
- Internet access for retrieving Pokémon information from PokéAPI.
- Available ports:
  - `8080` for the HTTPS Spring Boot application, unless another secure port is configured.
  - `5432` for PostgreSQL.

Maven is optional because the project includes the Maven Wrapper.

Optional database clients:

- [DBeaver](https://dbeaver.io/)
- [pgAdmin](https://www.pgadmin.org/)

> If the application uses a self-signed certificate in development, the browser or API client may display a certificate warning. This is expected unless the certificate is trusted locally.

---

## System Entities

### Table: `usuario`

| Column | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | Primary key, auto-generated | Unique user identifier |
| `nombre` | `VARCHAR(255)` | Not null | User's full name |
| `edad` | `INTEGER` | Not null | User's age |
| `email` | `VARCHAR(255)` | Not null, unique | User's email address |
| `pokemon_ids` | `VARCHAR(255)` | Nullable | Serialized list of Pokémon IDs |

> The final table and column names depend on the JPA entity mappings and database configuration.

---

## Request Body Structure

The `POST` and `PUT` endpoints expect a JSON request with the following header:

```http
Content-Type: application/json
```

### Example request body

```json
{
  "nombre": "Ricardo",
  "edad": 30,
  "email": "rdl@mail.com",
  "pokemonIds": [40, 45]
}
```

### Request fields

| Field | Type | Required | Description |
| :--- | :--- | :--- | :--- |
| `nombre` | `String` | Yes | User's full name |
| `edad` | `Integer` | Yes | User's age |
| `email` | `String` | Yes | User's email address |
| `pokemonIds` | `Array<Integer>` | No | List of associated Pokémon IDs |

---

## Application Routes (Endpoints)

The API provides the following CRUD operations over HTTPS:

| HTTP Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/usuarios` | Creates a new user |
| `GET` | `/api/usuarios` | Retrieves all users |
| `GET` | `/api/usuarios/{id}` | Retrieves a user by ID |
| `PUT` | `/api/usuarios/{id}` | Updates an existing user |
| `DELETE` | `/api/usuarios/{id}` | Deletes a user by ID |

### Create a user

```http
POST https://localhost:8080/api/usuarios
```

Creates a new user.

```bash
curl -k -X POST "https://localhost:8080/api/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ricardo",
    "edad": 30,
    "email": "rdl@mail.com",
    "pokemonIds": [40, 45]
  }'
```

> The `-k` option allows testing with a self-signed local certificate. Do not use it in production unless the certificate validation policy explicitly allows it.

### Retrieve all users

```http
GET https://localhost:8080/api/usuarios
```

Returns all users stored in the database.

```bash
curl -k "https://localhost:8080/api/usuarios"
```

### Retrieve a user by ID

```http
GET https://localhost:8080/api/usuarios/{id}
```

Returns a specific user by ID, including enriched Pokémon information when available.

```bash
curl -k "https://localhost:8080/api/usuarios/1"
```

### Update a user

```http
PUT https://localhost:8080/api/usuarios/{id}
```

Updates an existing user identified by `{id}`.

```bash
curl -k -X PUT "https://localhost:8080/api/usuarios/1" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ricardo Actualizado",
    "edad": 31,
    "email": "ricardo.actualizado@mail.com",
    "pokemonIds": [40, 45, 150]
  }'
```

### Delete a user

```http
DELETE https://localhost:8080/api/usuarios/{id}
```

Deletes an existing user identified by `{id}`.

```bash
curl -k -X DELETE "https://localhost:8080/api/usuarios/1"
```

### Interactive API documentation

When the application is running locally, Swagger UI is available at:

[Open Swagger UI](https://localhost:8080/swagger-ui/index.html)

The OpenAPI specification is available at:

[OpenAPI JSON](https://localhost:8080/v3/api-docs)

---

## Standards and Design Patterns

### Clean Architecture

The project is organized into layers with clearly defined responsibilities:

- **Domain:** Business entities and rules.
- **Application:** Use cases and business orchestration.
- **Infrastructure:** Database access and external API clients.
- **Presentation:** REST controllers and HTTP-related concerns.

This structure keeps business logic independent from frameworks, databases and external services.

### REST principles

The API follows REST conventions:

- HTTP verbs are used according to the operation.
- URLs represent application resources.
- JSON is used for request and response bodies.
- Appropriate HTTP status codes are returned.
- Requests are stateless.
- API communication is secured through HTTPS.

---

## How to Run the Application

### 1. Clone the repository

Replace `<YOUR_REPOSITORY_URL>` with the actual repository URL:

```bash
git clone <YOUR_REPOSITORY_URL>
cd challenge-ejercicio
```

### 2. Configure HTTPS

Configure the certificate and HTTPS properties required by the application before starting it.

For example, Spring Boot commonly uses properties similar to:

```properties
server.port=8080
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=YOUR_KEYSTORE_PASSWORD
server.ssl.key-store-type=PKCS12
server.ssl.key-alias=YOUR_KEY_ALIAS
```

> Use environment variables or a secure secret-management solution for real passwords. Do not commit private keys, certificates or passwords to the repository.

### 3. Start the application and database

Using Docker Compose:

```bash
docker compose up --build -d
```

If the project uses the legacy command:

```bash
docker-compose up --build -d
```

### 4. Check the container status

```bash
docker compose ps
```

### 5. View the application logs

Replace `challenge-backend` with the actual container name if necessary:

```bash
docker logs -f challenge-backend
```

To list the running containers:

```bash
docker ps
```

### 6. Access the API

Open [Swagger UI](https://localhost:8080/swagger-ui/index.html) or test the API directly:

```bash
curl -k "https://localhost:8080/api/usuarios"
```

### 7. Stop the services

```bash
docker compose down
```

To stop the services and remove their volumes:

```bash
docker compose down -v
```

> The `-v` option removes Docker volumes and may delete persisted local database data.

---

## Data Seeding (Empty Database)

If the database is empty, create a user by sending a secure `POST` request to `/api/usuarios`.

### Example seed request

```bash
curl -k -X POST "https://localhost:8080/api/usuarios" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Ricardo",
    "edad": 30,
    "email": "rdl@mail.com",
    "pokemonIds": [40, 45]
  }'
```

### Seeding through Swagger

1. Start the application with HTTPS enabled.
2. Open [Swagger UI](https://localhost:8080/swagger-ui/index.html).
3. Select `POST /api/usuarios`.
4. Click **Try it out**.
5. Enter the JSON request body.
6. Click **Execute**.

### Database configuration

The application database is:

```text
challenge_db
```

The integration-test database is:

```text
challenge_db_test
```

Database URLs, users, passwords and ports should be configured through environment variables or application configuration files.

Do not commit real credentials, private keys or certificates to the repository.

---

## How to Run Tests

Integration tests are executed directly from the command line using the Maven Wrapper.

From the project root directory, run:

```powershell
.\mvnw test -Dtest=UserControllerIntegrationTest
```

This command executes all test cases defined in the `UserControllerIntegrationTest` class.

### Test environment

The integration tests use the isolated test database:

```text
challenge_db_test
```

Before running the tests, verify that:

- PostgreSQL is running.
- The test database is available.
- The test configuration points to `challenge_db_test`.
- The command is executed from the project root directory.

### Tested functionality

The integration test class verifies the user-related REST API, including:

- Creating users with `POST`.
- Retrieving all users with `GET`.
- Retrieving a user by ID with `GET`.
- Updating users with `PUT`.
- Deleting users with `DELETE`.
- Validating HTTP status codes and response content.
- Verifying persistence operations.
- Testing the integration between the controller, service and repository layers.

All test cases executed successfully.

### Run all tests

To execute every test class in the project, run:

```powershell
.\mvnw test
```

Maven generates detailed test reports in:

```text
target/surefire-reports
```

### Testing technologies

The project uses:

- **JUnit 5** for test cases and assertions.
- **Spring Boot Test** for loading the application context.
- **MockMvc** for testing REST controllers.
- **Mockito** for mocking dependencies when necessary.
- **Maven Surefire** for test execution and report generation.
- **PostgreSQL** through the isolated test database.

---

## Deployment Considerations

Before deploying the application, consider the following aspects:

### HTTPS and certificates

- Use a certificate issued by a trusted Certificate Authority in production.
- Do not use `curl -k` in production because it disables certificate validation.
- Protect private keys and keystore passwords.
- Configure secure TLS protocols and cipher suites.
- Redirect insecure traffic to HTTPS when an HTTP entry point exists.
- Renew certificates before they expire.

### Environment variables

Configure environment-specific values outside the source code:

- Database URL.
- Database username and password.
- Active Spring profile.
- HTTPS port.
- Keystore location.
- Keystore password.
- External API configuration.
- Logging level.

### Database migrations

For production environments, use a migration tool such as Flyway or Liquibase instead of relying exclusively on automatic schema generation.

### Database persistence

Use a persistent Docker volume or a managed PostgreSQL database to prevent data loss.

### Secrets

Never commit database passwords, API keys, access tokens, private keys, certificates or production connection strings to the repository.

Use environment variables or a dedicated secret-management service.

### PokéAPI availability

Because the application depends on PokéAPI, consider connection timeouts, error handling, retry policies, rate limits, caching and fallback behavior.

### Monitoring and security

For production deployments:

- Configure application and database health checks.
- Use structured logging and error monitoring.
- Define CPU and memory limits.
- Configure CORS for trusted frontend domains only.
- Enforce authentication and authorization when required.
- Apply secure HTTP headers.
- Expose the application through HTTPS.
- Consider using a reverse proxy such as NGINX or Traefik.

---

## Decisions Made

### Clean Architecture

Clean Architecture was selected to improve maintainability, testability and future extensibility by separating business rules from frameworks, databases and external services.

### JPA, Hibernate and Spring Data JPA

Hibernate is used as the JPA implementation through Spring Data JPA because it provides:

- **Abstraction and productivity:** `JpaRepository` reduces repetitive CRUD code.
- **Object-relational mapping:** Hibernate maps Java objects to PostgreSQL tables.
- **Maintainability and portability:** JPA reduces coupling to a specific database engine.
- **Automatic schema management:** Hibernate can synchronize entities and tables through `ddl-auto`.

> For production environments, Flyway or Liquibase is recommended instead of relying exclusively on automatic schema generation.

### Docker

Docker was selected to provide consistent, portable and reproducible development environments with service isolation.

### HTTPS security

HTTPS was implemented to encrypt communication between clients and the API and protect data while it is transmitted.

### JUnit 5

JUnit 5 was selected because it is widely used in Java projects and integrates with Spring Boot through `spring-boot-starter-test`.

### Mockito and MockMvc

- **Mockito** isolates components by mocking their dependencies.
- **MockMvc** tests Spring MVC controllers without requiring an external web server.

### PostgreSQL

PostgreSQL was selected because it provides strong data consistency, reliable transaction management, mature SQL support, Spring Data JPA compatibility and Docker integration.

### PokéAPI

PokéAPI was selected as the external data source for enriching user profiles with Pokémon information.

### Maven

Maven was selected for dependency management, build automation, test execution, packaging and Spring Boot integration.

---
