# Library Management System (LMS)

This project is a backend system for managing a library, built using Spring Boot, PostgreSQL, and Redis. It follows a modular, layered architecture with secure authentication, role-based access control, audit logging, and optimized data handling using caching and DTO mapping.

## About the Developer

I am a Computer Engineering graduate with a focus on backend development. This project reflects my understanding of designing secure REST APIs, layered architecture, database normalization, and real-world system requirements.

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security 6
- Spring Data JPA (Hibernate)
- PostgreSQL
- Redis
- Maven
- Postman (for testing)

## Features

- JWT-based authentication and authorization
- Role-based access control with `ADMIN`, `STAFF`, `LIBRARIAN`, `USER`
- Modular architecture (Controllers, Services, Repositories, DTOs)
- Database relationships based on a normalized ERD
- Audit logging for authentication events (success/failure)
- Redis caching for performance optimization (e.g., category trees, dashboard data)
- Scheduled job support (e.g., overdue transactions)
- DTOs for safe and clean API communication
- Postman environment and automated test suite
- Endpoint access is strictly permission-controlled

## ERD Overview

Refer to the `public.png` file for the complete entity relationship diagram.

Highlights:

- `users` to `roles` is a many-to-many relationship via `user_roles`
- `books` to `authors` is many-to-many
- `categories` support self-referencing parent-child relationships
- Transaction logs are recorded with full audit details
- Each book is linked to a publisher, category, and multiple authors

## API Structure

Here is a summary of key modules and their endpoints:

### Authentication
- `POST /auth/signup` - Public user registration
- `POST /auth/login` - JWT token issuance for all roles

### User Management (Admin/Staff only)
- `POST /api/users/staff` - Create staff
- `POST /api/users/librarian` - Create librarian
- `GET /api/users` - List all users

### Authors
- `GET /api/authors`
- `POST /api/authors`
- `PUT /api/authors/{id}`
- `DELETE /api/authors/{id}`

### Books
- `POST /api/books`
- `GET /api/books`
- `GET /api/books/search` (supports keyword and semantic queries)

### Categories
- `GET /api/categories`
- `GET /api/categories/tree` - Hierarchical data for UI rendering

### Publishers
- `GET /api/publishers`
- `POST /api/publishers`

### Members
- `POST /api/members`
- `GET /api/members`
- `PUT /api/members/{id}`
- `DELETE /api/members/{id}`

### Borrowing Transactions
- `POST /api/transactions/borrow`
- `PUT /api/transactions/return/{transactionId}`
- `GET /api/transactions/overdue`

### Dashboard and Logs
- `GET /api/dashboard` - Aggregated counts (books, members, overdue)
- `GET /api/logs` - Audit logs (Admin only)

## Security Model

- JWT authentication is handled via `JwtAuthenticationFilter`
- All routes are protected by Spring Security with `@PreAuthorize` annotations
- Only `USER` can register via `/signup`, others are created internally
- Tokens are passed as Bearer headers
- Roles are defined in an enum (`ERole`) and linked to each user

## Audit Logging

Implemented using `AuthenticationEventListener` which tracks:

- Successful login (`LOGIN_SUCCESS`)
- Failed login attempts (`LOGIN_FAILURE`)

These are persisted in the `audit_logs` table with timestamp, action, role, and username.

## Redis Caching

- Categories and dashboard data are cached for performance
- Spring Cache abstraction used with Redis backend

## Testing

Postman files are provided:

- **Collection**: `LMS Logical Test Suite.postman_collection.json`
- **Environment**: `LMS Local Environment.postman_environment.json`

Tokens are stored in environment variables automatically via test scripts for each role. All edge cases, access restrictions, and role-based operations are included in the test suite.

## How to Run

1. Clone the repository
2. Update `application.properties` with your PostgreSQL and Redis settings
3. Start the database and Redis locally (or use Docker)
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
