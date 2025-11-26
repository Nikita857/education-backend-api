# GEMINI.md

## Project Overview

This is a Spring Boot application that provides an education service. It allows users to enroll in courses, track their progress, and obtain certificates. The application is built with Java 21, Spring Boot, and uses a PostgreSQL database for data storage. Authentication is handled using Spring Security and JWT. The frontend is likely built with a JavaScript framework and served by the nginx server, but the frontend code is not included in this repository.

### Key Technologies

*   **Backend:** Java 21, Spring Boot
*   **Database:** PostgreSQL
*   **Authentication:** Spring Security, JWT
*   **Build Tool:** Maven
*   **Containerization:** Docker, Docker Compose
*   **Reverse Proxy:** nginx

## Building and Running

### Prerequisites

*   Java 21
*   Maven
*   Docker
*   Docker Compose

### Running the Application

1.  **Start the application using Docker Compose:**

    ```bash
    docker-compose up -d
    ```

2.  **The application will be available at** `http://localhost:8080`.

### Building the Application

To build the application without running it, you can use the following Maven command:

```bash
./mvnw clean install
```

## Development Conventions

### API Design

The application follows a standard RESTful API design. The API endpoints are organized under the `/api/v1` path.

### Authentication and Authorization

*   Authentication is handled using JWT.
*   Authorization is implemented using Spring Security's `@PreAuthorize` annotation.
*   The following roles are used in the application:
    *   `USER`
    *   `INSTRUCTOR`
    *   `ADMIN`
    *   `HR`

### Coding Style

The code follows standard Java conventions. The use of Lombok helps to reduce boilerplate code.
