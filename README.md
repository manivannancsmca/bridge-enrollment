
# Bridge Enrollment API

A full CRUD REST API for managing student enrollments, built with Spring Boot 4.0.6 and Java 21.

---

## Tech Stack

| Layer        | Technology                              |
|--------------|-----------------------------------------|
| Framework    | Spring Boot 4.0.6                       |
| Language     | Java 21                                 |
| Database     | MySQL                                   |
| ORM          | Spring Data JPA / Hibernate             |
| API Docs     | SpringDoc OpenAPI (Swagger UI)          |
| Utilities    | Lombok, Spring DevTools, Validation     |

---

## Project Structure

```
bridge-enrollment/
├── src/main/java/com/bridge/enrollment/
│   ├── BridgeEnrollmentApplication.java   ← Main class
│   ├── SwaggerConfig.java                 ← OpenAPI configuration
│   ├── controller/
│   │   └── StudentController.java         ← REST endpoints
│   ├── service/
│   │   └── StudentService.java            ← Business logic
│   ├── repository/
│   │   └── StudentRepository.java         ← JPA queries
│   ├── model/
│   │   └── Student.java                   ← Entity
│   ├── dto/
│   │   ├── StudentRequestDTO.java         ← Input DTO
│   │   ├── StudentResponseDTO.java        ← Output DTO
│   │   └── ApiResponse.java               ← Generic wrapper
│   └── exception/
│       ├── ResourceNotFoundException.java
│       ├── DuplicateResourceException.java
│       └── GlobalExceptionHandler.java
└── src/main/resources/
    └── application.yml
```

---

## Database Setup

Create a MySQL database (or let it auto-create via the connection URL):

```sql
CREATE DATABASE bridge_enrollment_db;
```

Update credentials in `application.yml`:

```yaml
spring:
  datasource:
    username: your_username
    password: your_password
```

---

## Running the Application

```bash
mvn spring-boot:run
```

---

## Swagger UI

Once running, open:

```
http://localhost:8080/swagger-ui.html
```

---

## API Endpoints

| Method | Endpoint                          | Description                      |
|--------|-----------------------------------|----------------------------------|
| POST   | `/api/v1/students`                | Create a new student             |
| GET    | `/api/v1/students`                | Get all students                 |
| GET    | `/api/v1/students/{id}`           | Get student by ID                |
| GET    | `/api/v1/students/search?name=`   | Search students by name          |
| GET    | `/api/v1/students/status/{status}`| Get students by status           |
| PUT    | `/api/v1/students/{id}`           | Fully update a student           |
| PATCH  | `/api/v1/students/{id}/status`    | Update student status only       |
| DELETE | `/api/v1/students/{id}`           | Delete a student                 |

---

## Sample Request Body (POST / PUT)

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+919876543210",
  "dateOfBirth": "2000-05-15",
  "address": "123 Main St, Chennai, Tamil Nadu",
  "status": "ACTIVE"
}
```

## Enrollment Status Values

- `ACTIVE`
- `INACTIVE`
- `PENDING`
- `GRADUATED`
- `SUSPENDED`

