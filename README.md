# Cash Flow: Strategic Financial Management

This repository contains the complete Spring Boot backend for **Cash Flow**, a sophisticated Android application engineered for strategic expense tracking, budget orchestration, and in-depth spending analysis.

This platform consolidates diverse financial accounts into a single, unified dashboard, converting raw financial data into granular, actionable insights. It empowers users to execute their savings strategies and make informed fiscal decisions with absolute confidence.

## Table of Contents

  - [Features](https://www.google.com/search?q=%23features)
  - [Tech Stack](https://www.google.com/search?q=%23tech-stack)
  - [Database Schema](https://www.google.com/search?q=%23database-schema)
  - [Project Structure](https://www.google.com/search?q=%23project-structure)
  - [Configuration & Setup](https://www.google.com/search?q=%23configuration--setup)
  - [How to Run (Local)](https://www.google.com/search?q=%23how-to-run-local)
  - [How to Run (Docker)](https://www.google.com/search?q=%23how-to-run-docker)
  - [API Endpoints](https://www.google.com/search?q=%23api-endpoints)
  - [License](https://www.google.com/search?q=%23license)

## Features

  * **JWT Security:** Secure REST API using Spring Security 6 and JSON Web Tokens.
  * **Role-Based Access Control:** Differentiates between `USER` and `ADMIN` roles.
  * **Account-Type Restriction:** Premium features can be restricted to `PREMIUM` account types.
  * **Full CRUD Operations:** Comprehensive management for transactions, categories, and payment modes.
  * **Smart Ledger:** Calculates credits and debits for each payment mode.
  * **Shared & Custom Data:** Supports default categories (e.g., "Food") and user-created custom categories.
  * **File Uploads:** Attach receipts (via Cloudinary) to any transaction.
  * **Tagging System:** Many-to-many relationship for tagging transactions.

## Tech Stack

  * **Framework:** Spring Boot 3
  * **Language:** Java 21 (LTS)
  * **Database:** PostgreSQL
  * **Security:** Spring Security 6, JWT (jjwt)
  * **Data:** Spring Data JPA (Hibernate)
  * **Build Tool:** Apache Maven
  * **Containerization:** Docker
  * **File Storage:** Cloudinary
  * **Utilities:** Lombok, ModelMapper, Jakarta Bean Validation

## Database Schema

The database is designed to be efficient and scalable, separating user-specific data from shared default data.

```dbml
//// ----------------------------------------------------------------------------------
//// Optimized Database Schema for Personal Finance Tracker
//// ----------------------------------------------------------------------------------

Table User {
  id bigint [pk, increment]
  firstName varchar(100)
  lastName varchar(100)
  email varchar(255) [unique, not null]
  password varchar(255) [not null]
  imageUrl varchar(2048)
  role UserRole [not null]
  accountType AccountType [not null]
  created_at timestamptz [not null, default: `now()`]
  updated_at timestamptz [not null, default: `now()`]
}

Table PaymentMode {
  id bigint [pk, increment]
  modeName varchar(100) [not null]
  paymentType PaymentPlatform [not null]
  userId bigint [ref: > User.id, null] // Null for default modes
  created_at timestamptz [not null, default: `now()`]
}

Table Category {
  id bigint [pk, increment]
  name varchar(100) [not null]
  imageUrl varchar(2048)
  categoryType TransactionType [not null]
  userId bigint [ref: > User.id, null] // Null for default categories
  created_at timestamptz [not null, default: `now()`]
}

Table Transaction {
  id bigint [pk, increment]
  type TransactionType [not null]
  time timestamptz [not null, default: `now()`]
  amount decimal(12, 2) [not null]
  description text
  userId bigint [ref: > User.id, not null]
  categoryId bigint [ref: > Category.id, null]
  fromPaymentModeId bigint [ref: > PaymentMode.id, null]
  toPaymentModeId bigint [ref: > PaymentMode.id, null]
  created_at timestamptz [not null, default: `now()`]
  updated_at timestamptz [not null, default: `now()`]
}

Table Tag {
  id bigint [pk, increment]
  name varchar(50) [not null]
  userId bigint [ref: > User.id, not null]
  created_at timestamptz [not null, default: `now()`]
}

Table Attachment {
  id bigint [pk, increment]
  fileName varchar(255) [not null]
  fileUrl varchar(2048) [not null]
  fileType varchar(100)
  uploadTime timestamptz [not null, default: `now()`]
  transactionId bigint [ref: > Transaction.id, not null]
}

Table Transaction_Tag {
  transactionId bigint [ref: > Transaction.id]
  tagId bigint [ref: > Tag.id]
  pk: [transactionId, tagId]
}

// --- ENUM Definitions ---
Enum UserRole { USER, ADMIN }
Enum AccountType { FREE, PREMIUM }
Enum PaymentPlatform { BANK, WALLET, CASH, CREDIT_CARD, OTHER }
Enum TransactionType { INCOME, EXPENSE, TRANSFER }
```

## Project Structure

The project follows a standard layered architecture for separation of concerns.

```
src/main/java/com/vedalvi/CashFlow
│
├── config/              # Spring Security, JWT, and Application beans
│   ├── ApplicationConfig.java
│   └── SecurityConfig.java
│
├── controller/          # REST API endpoints
│   ├── AuthController.java
│   ├── AttachmentController.java
│   ├── CategoryController.java
│   ├── PaymentModeController.java
│   └── TransactionController.java
│
├── dto/                 # Data Transfer Objects for API requests/responses
│   ├── CategoryDto.java
│   ├── PaymentModeDto.java
│   ├── TransactionRequestDto.java
│   ├── ...
│
├── exception/           # Global exception handling
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
│
├── model/               # JPA Entities and Enums
│   ├── Attachment.java
│   ├── Category.java
│   ├── PaymentMode.java
│   ├── Tag.java
│   ├── Transaction.java
│   ├── User.java
│   └── enums/
│       ├── AccountType.java
│       ├── ...
│
├── repository/          # Spring Data JPA repositories
│   ├── AttachmentRepository.java
│   ├── CategoryRepository.java
│   ├── ...
│
├── security/            # JWT and UserDetails implementation
│   ├── CustomUserDetails.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtService.java
│   └── UserDetailsServiceImpl.java
│
├── service/             # Service interfaces (contracts)
│   ├── AttachmentService.java
│   ├── CategoryService.java
│   ├── ...
│
└── service/impl/        # Service implementations (business logic)
    ├── AttachmentServiceImpl.java
    ├── CategoryServiceImpl.java
    ├── ...
```

## Configuration & Setup

Before running the application, you must set up your environment variables. The application uses environment variables for all sensitive configuration.

Create an `application.properties` file in `src/main/resources/` or set these as system environment variables.

```properties
# ---------------------------------
# PostgreSQL Database Configuration
# ---------------------------------
# Use separate databases for dev and test
spring.datasource.url=jdbc:postgresql://<YOUR_HOST>:5432/cashflow_db
spring.datasource.username=<YOUR_USERNAME>
spring.datasource.password=<YOUR_PASSWORD>

# ---------------------------------
# JPA & Hibernate Configuration
# ---------------------------------
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true

# ---------------------------------
# JWT Secret Configuration
# ---------------------------------
# Generate a strong Base64-encoded key (e.g., openssl rand -base64 32)
application.security.jwt.secret-key=<YOUR_SUPER_SECRET_JWT_KEY>
application.security.jwt.expiration=86400000 # 24 hours in ms

# ---------------------------------
# Cloudinary (for file uploads)
# ---------------------------------
cloudinary.url=cloudinary://<API_KEY>:<API_SECRET>@<CLOUD_NAME>
```

## API Endpoints

All endpoints (except `/api/auth/**`) require a valid `Bearer Token` in the `Authorization` header.

### Authentication (`/api/auth`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/signup` | Register a new user. |
| `POST` | `/api/auth/login` | Log in and receive a JWT. |

### Categories (`/api/categories`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/categories` | Get all default and user-specific categories. |
| `POST` | `/api/categories` | Create a new custom category. |
| `PUT` | `/api/categories/{id}` | Update a custom category (must be owner). |
| `DELETE` | `/api/categories/{id}` | Delete a custom category (must be owner). |

### Payment Modes (`/api/payment-modes`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/payment-modes` | Get all default and user-specific payment modes. |
| `POST` | `/api/payment-modes` | Create a new custom payment mode. |
| `PUT` | `/api/payment-modes/{id}` | Update a custom payment mode (must be owner). |
| `DELETE` | `/api/payment-modes/{id}` | Delete a custom payment mode (must be owner). |
| `GET` | `/api/payment-modes/{id}/ledger` | Get all credits and debits for a payment mode. |

### Transactions (`/api/transactions`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/transactions` | Get all transactions for the logged-in user. |
| `POST` | `/api/transactions` | Create a new transaction (Income, Expense, or Transfer). |
| `GET` | `/api/transactions/{id}` | Get a single transaction by ID (must be owner). |
| `PUT` | `/api/transactions/{id}` | Update a transaction (must be owner). |
| `DELETE` | `/api/transactions/{id}` | Delete a transaction (must be owner). |

### Attachments

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/transactions/{transactionId}/attachments` | Upload an attachment (e.g., receipt) for a transaction. |
| `GET` | `/api/transactions/{transactionId}/attachments` | Get all attachments for a transaction. |
| `DELETE` | `/api/attachments/{attachmentId}` | Delete an attachment (must be owner). |


## How to Run (Local)

1.  **Prerequisites:**

      * Java JDK 21
      * Apache Maven 3.8+
      * PostgreSQL running and accessible

2.  **Clone the Repository:**

    ```bash
    git clone https://github.com/vedantdalvi45/CashFlow.git
    cd CashFlow
    ```

3.  **Database Setup:**

      * Log in to PostgreSQL.
      * Create a database for development: `CREATE DATABASE cashflow_db;`
      * Create a separate database for testing: `CREATE DATABASE cashflow_test;`

4.  **Configure Environment:**

      * Set the environment variables as described in the [Configuration](https://www.google.com/search?q=%23configuration--setup) section. This is critical.

5.  **Build and Run the Application:**

    ```bash
    ./mvnw clean package
    java -jar target/CashFlow-0.0.1-SNAPSHOT.jar
    ```

    Alternatively, you can run directly with the Maven wrapper:

    ```bash
    ./mvnw spring-boot:run
    ```

The application will start on `http://localhost:8080`.

## How to Run (Docker)

The included `Dockerfile` builds a multi-stage, optimized image.

1.  **Build the Docker Image:**

    ```bash
    docker build -t vedantdalvi45/cashflow .
    ```

2.  **Run the Container:**
    You must pass the environment variables to the container at runtime.

    ```bash
    docker run -d -p 8080:8080 \
      -e SPRING_DATASOURCE_URL="jdbc:postgresql://<YOUR_HOST_IP>:5432/cashflow_db" \
      -e SPRING_DATASOURCE_USERNAME="<YOUR_USERNAME>" \
      -e SPRING_DATASOURCE_PASSWORD="<YOUR_PASSWORD>" \
      -e APPLICATION_SECURITY_JWT_SECRET_KEY="<YOUR_SUPER_SECRET_JWT_KEY>" \
      -e CLOUDINARY_URL="<YOUR_CLOUDINARY_URL>" \
      --name cashflow-api \
      vedantdalvi45/cashflow
    ```

    > **Note:** If connecting to a PostgreSQL instance on your host machine, use your host's network IP, not `localhost`.
