# Spring E-Commerce Project

This project is a Spring Boot-based e-commerce backend built for user authentication, product management, and protected secure APIs. It uses Spring Security with JWT-based authorization to protect application endpoints.

## Tech Stack

- Java 27
- Spring Boot 4.2.0-M2
- Spring Web MVC
- Spring Data JPA
- Spring Security
- MySQL Database
- Maven
- JJWT (Java JWT library)
- Lombok
- BCrypt password hashing

## Project Features

- User registration
- User login
- JWT-based authentication
- Stateless security configuration
- Product CRUD endpoints
- Protected routes for authenticated users
- MySQL persistence for application data

## Dependencies Used

The project uses the following key libraries from the Maven configuration:

- `spring-boot-starter-security`  
  Provides Spring Security for authentication and authorization.

- `spring-boot-starter-webmvc`  
  Provides Web MVC support and REST API features.

- `spring-boot-starter-data-jpa`  
  Enables JPA and database access.

- `mysql-connector-j`  
  Connects the application to a MySQL database.

- `jjwt-api`, `jjwt-impl`, `jjwt-jackson`  
  Used for generating, parsing, validating, and signing JWT tokens.

- `spring-boot-devtools`  
  Helps with local development auto-reload support.

- `lombok`  
  Reduces boilerplate code during Java development.

## Authentication Flow

This application uses a custom JWT-based authentication flow.

### 1. User login
When a client sends a POST request to `/login`, the backend:

- receives the username and password,
- authenticates using the `AuthenticationManager`,
- verifies the user against the database through `MyUserDetailsService`,
- and if successful, generates a JWT token.

### 2. JWT generation
The `JwtService` class creates a token using the username as the subject and signs it with an HMAC SHA-256 secret key.

Example behavior:

- subject = username
- issued at = current timestamp
- expiration = 5 minutes from issue time
- signed with HS256

### 3. Sending the token
The client stores the returned token and sends it on later requests in the Authorization header:

```http
Authorization: Bearer <jwt-token>
```

### 4. Request validation
The `JwtFilter` runs before every secured request and:

- reads the `Authorization` header,
- checks whether it starts with `Bearer `,
- extracts the token,
- reads the username from the token,
- loads the matching user details,
- validates the token signature and expiration,
- if valid, sets the Spring Security authentication context.

### 5. Protected endpoints
Routes that are not explicitly public (`/register`, `/login`) are secured by Spring Security and require a valid JWT.

If the JWT is missing, expired, invalid, or signed incorrectly, the request is rejected with a `401 Unauthorized` response.

## Security Configuration

The app configures Spring Security with:

- CSRF disabled for stateless API usage
- public endpoints: `/register`, `/login`
- all other endpoints require authentication
- stateless session management
- JWT filter added before `UsernamePasswordAuthenticationFilter`

## Database Configuration

The application connects to MySQL through the configuration in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/project_ecom
spring.datasource.username=root
spring.datasource.password=12345
```

## Running the Project

From the project root, run:

```bash
./mvnw spring-boot:run
```

Or build the project:

```bash
./mvnw clean install
```

## Swagger Documentation

Once the application is running, you can access the Swagger UI and OpenAPI docs here:

- Swagger UI: http://localhost:8080/swagger-ui.html
- Swagger UI (alternate path): http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

Use the Authorization button in Swagger UI and paste the JWT as:

```http
Bearer <your-jwt-token>
```

## Useful Endpoints

- `POST /register` - register a user
- `POST /login` - login and receive JWT
- `GET /hello` - sample endpoint
- `GET /api/products` - list products

## Notes

- The JWT secret is stored in the application code and should be moved to environment variables or a secure configuration system in production.
- The application currently uses BCrypt for password hashing, which is recommended for secure password storage.
