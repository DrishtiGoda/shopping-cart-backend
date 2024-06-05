## Shopping Cart Backend Application

## Overview
This is a Spring Boot application for managing a shopping cart. It provides APIs for adding, removing, and updating items in the cart, as well as viewing the items in the cart.

## Features

- **HTTP Status Code Handling**: The application handles appropriate HTTP status codes for different scenarios.
- **Exception Handling**: Exception handling is implemented using try-catch blocks to ensure graceful error handling.
- **Logging**: Logging is implemented using a logger framework to track important events and errors.
- **API Versioning**: The APIs are versioned to support backward compatibility and smooth upgrades.
- **JWT Token**: JSON Web Token (JWT) is used for authentication and authorization of API requests.
- **Unit Testing**: Test cases are included to ensure the correctness of the implemented functionalities.
- **Continuous Integration Pipeline**: A CI pipeline is set up for automated testing and deployment.
- **Pagination**: Pagination is implemented for endpoints returning a large number of results.
- **Swagger UI**: Swagger UI is integrated to provide an interactive documentation for the APIs.
- **Docker**: Docker is used for containerizing the application to ensure consistency across different environments.

## Shopping Cart UML Diagram
<img src="src/main/resources/static/Shopping%20Cart.png" alt="Shopping Cart" height="500" />

## Technologies Used

- Java 
- Spring Boot
- PostgreSQL
- Docker 

## Installation

#### I. Docker Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/DrishtiGoda/shopping-cart-backend.git
    ```

2. **Build and Run with Docker Compose**:
    ```bash
    cd shopping-cart-backend
    docker-compose up --build
    ```

3. **Explore the API**: Navigate to `http://localhost:8080/swagger-ui.html` to explore the API documentation using Swagger UI.

##

####  II. Local Installation

1. **Clone the repository**:
    ```bash
    git clone https://github.com/DrishtiGoda/shopping-cart-backend.git
    ```

2. **Set up PostgreSQL**: Ensure you have PostgreSQL installed and running. Update the `application.properties` file with your PostgreSQL database configuration.


3. **Build and Run the Application**:
    ```bash
    cd shopping-cart-backend
    ./mvnw spring-boot:run
    ```

4. **Explore the API**: Navigate to `http://localhost:8080/swagger-ui.html` to explore the API documentation using Swagger UI.
