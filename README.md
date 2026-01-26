![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
# Banking Service API

A robust RESTful backend application built with **Spring Boot 3** and **Java 21** to manage core banking operations. This project demonstrates full **CRUD functionality** and **complex relational database integration** with MySQL, support multiple account types. By leveraging modern Java feature like **Records**, the system provides a unified API that handles specialized business rules, such as overdraft limits and multi-account ownership, within a clean and scalable codebase.

## Features

- **Atomic Registration**: A single-endpoint orchestration that handles the creation of a `User`, their `Contact` details, and an initial `Account` simultaneously in one transaction.
- **Complex Entity Relationships**: Manages **One-to-One** (User-to-Contact) and **One-to-Many** (User-to-multiple Accounts) mappings, allowing a single user profile to own multiple bank accounts.
- **Smart Resource Cleanup**: Implements business-aware deletion logic—automatically removing the entire User profile and associated Contact info only when their last remaining account is closed.
- **Secure Transactions**: Facilitates deposits, withdrawals, and inter-account transfers with strict balance validation and business rule enforcement.
- **Concurrency Control**: Implements **Pessimistic Locking** (`SELECT FOR UPDATE`) to ensure data integrity during simultaneous financial operations on the same account.
- **Transactional Integrity**: Uses `@Transactional` to guarantee the Atomicity property, ensuring that multi-step operations (like money transfers) either succeed completely or roll back safely.
- **Global Error Handling**: Centralized exception management for `AccountException` and database lock timeouts, providing clean and descriptive JSON error responses.
- **Polymorphic Business Logic**: Automatically handles specialized rules based on account type, distinguishing between standard `SAVINGS` account constraints and `CURRENT` accounts with overdraft protection.
- **Data Persistence**: Utilizes Spring Data JPA to store and manage relational data within a MySQL database.
- **Modern RESTful Design**: Follows standard HTTP methods and clean API routing, leveraging **Java 21 Records** for immutable and concise Data Transfer Objects (DTOs).

## Technology Stack
- **Language**: Java 21 (using **Records**)
- **Framework**: Spring Boot 3 (Data JPA, Web)
- **Database**: MySQL
- **Build Tool**: Maven, Lombok (for boilerplate reduction)
- **Mapping**: Manual Mapper implementation for Entity-DTO conversion
- **Testing**: Postman

## Project Structure
- `src/main/java`:
   - `Controller`: REST API entry points (`AccountController`).
   - `Entity`: Relational data models (`User`, `Contact`, `Account`).
   - `Dto`: Immutable Java Records for data transfer.
   - `Mapper`: Logic for converting between Entities and DTOs.
   - `Repository`: Data access layer with Pessimistic Locking queries.
   - `Service`: Core business logic and transaction orchestration.
   - `Exception`: Centralized global error handling.
- `src/main/resources`: Contains database configurations.
- `Banking_API_Service.postman_collection.json`: A pre-configured Postman collection for testing the API.

## API Endpoints

### User & Profile Management
| Method | Endpoint | Description |
|:-------|:---------|:------------|
| **POST** | `/api/accounts/register` | Register a new User with Contact info and Primary Account |
| **GET** | `/api/accounts/user/{userId}` | Retrieve full User profile (includes nested Contact details) |
| **POST** | `/api/accounts/user/{userId}/addAccount` | Add a secondary bank account to an existing User ID |

### Banking & Transactions
| Method | Endpoint | Description |
|:-------|:---------|:------------|
| **POST** | `/api/accounts/transfer` | Move funds between any two accounts (Atomic operation) |
| **PUT** | `/api/accounts/{id}/deposit` | Add money to a specific account balance |
| **PUT** | `/api/accounts/{id}/withdraw` | Remove money (validates Savings vs. Current overdraft rules) |
| **GET** | `/api/accounts/{id}` | Get specific account details by its Unique ID |
| **GET** | `/api/accounts/user/{userId}/accounts` | List all accounts belonging to a specific User |

### Administrative & Metadata
| Method | Endpoint | Description |
|:-------|:---------|:------------|
| **GET** | `/api/accounts` | Retrieve all accounts in the system (Admin View) |
| **GET** | `/api/accounts/{id}/type` | Check if an account is `SAVINGS` or `CURRENT` |
| **DELETE** | `/api/accounts/{id}` | Close an account (Auto-deletes User if it's their last one) |

## Concurrency & Security
To ensure the safety of funds in a multi-user environment, this API implements:
- **Pessimistic Locking**: When a withdrawal or deposit starts, the specific account row is locked at the database level using `SELECT FOR UPDATE`. This prevents "Race Conditions" where two users might try to spend the same balance at once.
- **Lock Timeouts**: Configured via JPA Query Hints to prevent system hangs, returning a `503 Service Unavailable` if a lock cannot be acquired within 3 seconds.
- **Custom Exceptions**: Handlers for `AccountException` and `PessimisticLockingFailureException` to provide clear, machine-readable error codes.
- **Lazy Loading**: Uses `FetchType.LAZY` for account-to-user mappings to optimize database performance and prevent unnecessary data retrieval.

## Setup & Installation
1. **Clone the repository**:
   ```bash
   git clone [https://github.com/yuvrajksinha/Banking_Service_Spring_Boot.git](https://github.com/yuvrajksinha/Banking_Service_Spring_Boot.git)
2. **Configure Database Credentials**: Open `src/main/resources/application.properties` and update the following lines with your local MySQL credentials:
   ```properties
   spring.datasource.username = your_username
   spring.datasource.password = your_password
3. **Run the Application**: You can run the project using **Maven** via the terminal or simply run the `BankingServiceApplication.java` file from your IDE:
   ```bash
   mvn spring-boot:run
   
## Testing with Postman
1. **Registration**: Start by using the `POST /register` request to create your first user. This will generate a `userId`.
2. **Add Accounts**: Use the generated `userId` to test adding secondary accounts via `POST /user/{userId}/addAccount`.
3. **Nested Profile**: Check the `GET /user/{userId}` endpoint to see how the system maps the User and Contact details into a single response.
