![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%234479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
# Banking Service API

A robust RESTful backend application built with **Spring Boot 3** and **Java 21** to manage core banking operations. This project demonstrates full **CRUD functionality** and **database integration with MySQL**, support multiple account types. By leveraging modern Java feature like **Records**, the system provides a unified API that handles specialized business rules, such as overdraft limits, within a clean and scalable codebase.

## Features
- **Account Management**: Create, retrieve, and delete bank accounts.
- **Transactions**: Securely deposit and withdraw funds from existing accounts.
- **Data Persistence**: Uses Spring Data JPA to store account information in a MySQL database.
- **RESTful Design**: Follows standard HTTP methods and clean API routing.
- **Concurrency Control**: Implements **Pessimistic Locking** to ensure data integrity during simultaneous withdrawals and deposits.
- **Transactional Integrity**: Uses `@Transactional` to guarantee Atomicity property, ensuring no data loss during banking operations.
- **Global Error Handling**: Centralized exception management for account validation and database lock timeouts.
- **Polymorphic Accounts**: Supports both `SavingsAccount` (standard rules) and `CurrentAccount` (with overdraft protection). 
- **Automatic Type Handling**: Handle business logic based on account type.

## Technology Stack
- **Language**: Java 21
- **Framework**: Spring Boot 3
- **Database**: MySQL
- **Build Tool**: Maven
- **Testing**: Postman

## Project Structure
- `src/main/java`: Contains the controller, service, and repository layers.
- `src/main/resources`: Contains database configurations.
- `Banking_API_Service.postman_collection.json`: A pre-configured Postman collection for testing the API.

## API Endpoints
| Method | Endpoint                      | Description                                   |
|:-------|:------------------------------|:----------------------------------------------|
| POST   | `/api/accounts`               | Create a new bank account                     |
| GET    | `/api/accounts/{id}`          | Get account details by ID                     |
| GET    | `/api/accounts`               | Get all accounts                              |
| GET    | `/api/accounts/{id}/type`     | Retrieve type (Savings/Current) of an account |
| PUT    | `/api/accounts/{id}/deposit`  | Add money to an account                       |
| PUT    | `/api/accounts/{id}/withdraw` | Remove money from an account                  |
| DELETE | `/api/accounts/{id}`          | Close/Delete an account                       |

## Concurrency & Security
To ensure the safety of funds in a multi-user environment, this API implements:
- **Pessimistic Locking**: When a withdrawal or deposit starts, the specific account row is locked at the database level using `SELECT FOR UPDATE`. This prevents "Race Conditions" where two users might try to spend the same balance at once.
- **Lock Timeouts**: Configured via JPA Query Hints to prevent system hangs, returning a `503 Service Unavailable` if a lock cannot be acquired within 3 seconds.
- **Custom Exceptions**: Handlers for `AccountException` and `PessimisticLockingFailureException` to provide clear, machine-readable error codes.

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

A pre-configured Postman collection is included in the root directory: `Banking_API_Service.postman_collection.json`.

* **Import** this file into Postman.
* **Ensure** the application is running on `http://localhost:8080`.
* **Use** the saved requests to test all API functionalities (Create, Deposit, Withdraw, etc.).
