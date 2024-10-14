# Simple Banking 

The `Simple Banking` is a Spring Boot application designed to manage banking operations 
such as account credits, debits, and phone bill payments. It includes transaction tracking 
and retrieval functionalities. The system provides a REST API to manage bank accounts 
and transactions, making use of tools like Mapper utilities for entity conversions.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17
- Spring Boot
- Spring Data JPA for database interaction.
- Lombok for simplifying Java class definitions.
- Mockito for unit testing.

# Java and Spring Boot Coding Standards
## 1. Code Formatting

- **Indentation:**
    - Use tabs for indentation, not spaces.
- **Braces:**
    - Always use braces, even for single statement blocks.
- **Line Length:**
    - Keep lines to a maximum of 120 characters.
- **Whitespace:**
    - Avoid trailing whitespaces.

## 2. Naming Conventions

- **Classes:**
    - Start with an uppercase and use CamelCase.
    - *Example:* `UserService`
- **Commit Messages:**
    - *Example:* `BankAccount is created`
- **Methods:**
    - Start with a lowercase and use camelCase.
    - *Example:* `debit()`
- **Variables:**
    - Use meaningful names and avoid single-letter names (except for loop indexes).
    - *Example:* `accountNumber`, `approvalCode`

## 3. Comments

- Write meaningful comments and avoid obvious comments.
- Use Javadoc style comments for classes and methods.
- Comment any code that might appear non-trivial or has business implications.

## 4. Spring Boot Specifics

- **Property Injection:**
    - Use constructor injection over field injection for better testability.
- **Exception Handling:**
    - Use `@ControllerAdvice` and `@ExceptionHandler` to handle exceptions globally.

## 5. General Guidelines

- **Avoid Magic Numbers:**
    - Instead of hard coded numbers, use named constants.
- **Null Safety:**
    - Always check for `null` before using an object.
- **Logging:**
    - Use appropriate logging levels (INFO) and always log exceptions.
- **Unit Testing:**
    - Always write unit tests for your service layers. Aim for a high code coverage.

## 6. SOLID Principles
     
This application follows the SOLID design principles, which promote best practices in object-oriented programming and enhance code maintainability, flexibility, and readability.

- **Single Responsibility Principle (SRP):** 
- Each class in the application has a single responsibility. For example, the BankAccount class manages account details, while the TransactionService handles transaction operations.

- **Open/Closed Principle (OCP):** 
- The application is designed to be open for extension but closed for modification. New transaction types can be added through inheritance without altering existing code. 

- **Liskov Substitution Principle (LSP):**
- Subtypes can be used interchangeably with their base types without affecting the functionality of the application. 
- For instance, both DepositTransaction and WithdrawalTransaction can be treated as Transaction types, ensuring consistency and reliability.

- **Interface Segregation Principle (ISP):**
- Interfaces are designed to be client-specific, allowing classes to implement only the methods they need. 
- This reduces unnecessary dependencies and promotes a cleaner architecture. For example, separate interfaces for deposit and withdrawal functionalities enhance code clarity.

- **Dependency Inversion Principle (DIP):**
- The application relies on abstractions rather than concrete implementations. This allows for easier testing and flexibility, as different implementations can be substituted without modifying the service code.

By adhering to these SOLID principles, the application is more robust, easier to maintain, and adaptable to future changes.
## 7. Dependencies

- Keep your dependencies up to date.
- Avoid using deprecated libraries or methods.
- Use Gradle's `runtimeOnly` configuration to include libraries that are needed only at runtime and not during compile-time.

## 8. Database

- **Naming:**
    - Use `transactions` for table and column names.
- **Indexes:**
    - Always index foreign keys and frequently queried fields.
    - Example: If accountNumber is frequently used in queries for transactions, 
     index the accountNumber field in the Transaction table.
- **Lazy Loading:**
    - Be cautious when using lazy loading with Hibernate to avoid _N+1 problems_. 
     (Lazy loading can lead to inefficient database queries, 
    - especially in scenarios where multiple related entities are loaded separately. 
    - Consider using eager loading or fetching strategies to optimize data retrieval.)

## 9. Unit Testing

The application uses JUnit 5 and Mockito for writing and executing unit tests. The unit tests are written to verify 
the functionality of various service layers, ensuring that methods behave as expected under different scenarios.

- **Testing Frameworks Used:**
  - JUnit 5: The primary testing framework used to write and run unit tests.
  - Mockito: A mocking framework used to mock dependencies in unit tests, allowing isolated testing of components by simulating their behavior.
---
