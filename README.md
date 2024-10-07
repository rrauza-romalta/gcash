Exam

## Description
This project is a backend application (for GCash) built with Spring Boot for the backend. It uses MySQL as the database.


## Technologies Used
- Java
- Spring Boot
- Maven
- MySQL
- Docker

## Prerequisites
- Java JDK (17.x)
- Maven (3.9.5)
- Docker (27.0.3)

## Setup Instructions

### Local Development Deployment

#### Backend
1. Go to the root directory:
2. Build the project using Maven:
    ```sh
    mvn clean install
    ```
3. Start the mysql server and deploy the application using Docker Compose:
    ```sh
    docker-compose up --build
    ```

### Usage Development
- The backend API will be available at `http://localhost:8087`

### License
This project is licensed under the MIT License.