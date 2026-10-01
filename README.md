# Backend Challenge Course

A RESTful API developed with Java and Spring Boot following Clean Architecture principles. The system manages user data and integrates with the public [PokéAPI](https://pokeapi.co/) to enrich user profiles with Pokémon details.

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

---

## Technologies Used

* **Java 17 / 21**
* **Spring Boot 3.x** (Spring MVC, Data JPA)
* **PostgreSQL** (Relational Database Management System)
* **Docker & Docker Compose** (Database containerization)
* **JUnit 5, Mockito & MockMvc** (Unit and Integration testing)
* **Maven** (Dependency management and build tool)
* **SpringDoc OpenAPI / Swagger** (Interactive API documentation)

---

## Features

* **User Registration**: Registers new users while ensuring email uniqueness and associating a list of Pokémon IDs.
* **Enriched User Retrieval**: Fetches user records from the local database and dynamically integrates full Pokémon details in real-time via PokéAPI integration.
* **Environment Database Isolation**: Maintains distinct databases for development/production (`challange_db`) and integration testing (`challange_db_test`).
* **Integration Test Suite**: Automated controller tests featuring database cleanup and external REST call isolation using `@MockBean`.

---

## Prerequisites

Before running the application and tests, ensure you have the following installed:

* **JDK 17** or higher configured in system environment variables.
* **Apache Maven 3.8+** (optional if using the bundled wrapper `mvnw`).
* **Docker Desktop** (with Linux container support) and **Docker Compose**.
* Database GUI client (*DBeaver*, *pgAdmin*, or similar — optional).

---

## System Entities

### Table: `usuario`

| Column | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | `BIGINT` | `PRIMARY KEY`, `AUTO_INCREMENT` | Unique identifier for the user |
| `nombre` | `VARCHAR(255)` | `NOT NULL` | Full name of the user |
| `edad` | `INTEGER` | `NOT NULL` | Age of the user |
| `email` | `VARCHAR(255)` | `NOT NULL`, `UNIQUE` | User's email address |
| `pokemon_ids` | `VARCHAR(255)` | `NULLABLE` | Serialized list of assigned Pokémon IDs |

---

## Request Body Structure

When issuing `POST` requests to `/api/usuarios`, payloads must be sent as **JSON** with the header `Content-Type: application/json`.

### Example Payload:

```json
{
  "nombre": "Ricardo",
  "edad": 30,
  "correo": "rdl@mail.com",
  "pokemonIds": [40, 45]
}
