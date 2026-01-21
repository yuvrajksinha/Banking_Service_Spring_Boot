# Banking Service API

A RESTful backend application built with **Spring Boot 3** and **Java 21** to manage basic banking operations. This project demonstrates CRUD functionality, database integration with MySQL, and the use of modern Java features like Records.

## Features
- **Account Management**: Create, retrieve, and delete bank accounts.
- **Transactions**: Securely deposit and withdraw funds from existing accounts.
- **Data Persistence**: Uses Spring Data JPA to store account information in a MySQL database.
- **RESTful Design**: Follows standard HTTP methods and clean API routing.

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
| Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | `/api/accounts` | Create a new bank account |
| GET | `/api/accounts/{id}` | Get account details by ID |
| GET | `/api/accounts` | Get all accounts |
| PUT | `/api/accounts/{id}/deposit` | Add money to an account |
| PUT | `/api/accounts/{id}/withdraw` | Remove money from an account |
| DELETE | `/api/accounts/{id}` | Close/Delete an account |



## Setup & Installation
1. **Clone the repository**:
   ```bash
   git clone [https://github.com/yuvrajksinha/Banking_Service_Spring_Boot.git](https://github.com/yuvrajksinha/Banking_Service_Spring_Boot.git)
2. **Configure Database Credentials**: Open `src/main/resources/application.properties` and update the following lines with your local MySQL credentials:
   ```properties
   spring.datasource.username = your_username
   spring.datasource.password = your_password
3. **Run the Application**: You can run the project using **Maven** via the terminal Or simply run the `BankingServiceApplication.java` file from your IDE:
   ```bash
   mvn spring-boot:run
## Testing with Postman

A pre-configured Postman collection is included in the root directory: `Banking_API_Service.postman_collection.json`.

* **Import** this file into Postman.
* **Ensure** the application is running on `http://localhost:8080`.
* **Use** the saved requests to test all API functionalities (Create, Deposit, Withdraw, etc.).
