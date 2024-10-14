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

- **Single Responsibility Principle:**
    - A class should have only one reason to change.
- **Avoid Magic Numbers:**
    - Instead of hard coded numbers, use named constants.
- **Null Safety:**
    - Always check for `null` before using an object.
- **Logging:**
    - Use appropriate logging levels (INFO) and always log exceptions.
- **Unit Testing:**
    - Always write unit tests for your service layers. Aim for a high code coverage.

## 6. Dependencies

- Keep your dependencies up to date.
- Avoid using deprecated libraries or methods.
- Use Gradle's `runtimeOnly` configuration to include libraries that are needed only at runtime and not during compile-time.

## 7. Database

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

## 8. Unit Testing
      
    The application uses JUnit 5 and Mockito for writing and executing unit tests. The unit tests are written to verify 
    the functionality of various service layers, ensuring that methods behave as expected under different scenarios.

- **Testing Frameworks Used:**
  - JUnit 5: The primary testing framework used to write and run unit tests.
  - Mockito: A mocking framework used to mock dependencies in unit tests, allowing isolated testing of components by simulating their behavior.
---
