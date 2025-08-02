# 🧠 Quiz App Microservice

A modular and scalable Spring Boot-based microservices project that enables the creation, retrieval, and evaluation of quizzes. The architecture follows a clean separation of concerns and leverages:

🧭 Eureka for service discovery

🌐 Spring Cloud Gateway for intelligent routing

🧠 Spring Boot for robust service development

🗄️ MySQL for data persistence

<div align="center"> <img src="https://img.shields.io/badge/Java-24-blue?logo=java&logoColor=white" alt="Java" /> <img src="https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen?logo=springboot&logoColor=white" alt="Spring Boot" /> <img src="https://img.shields.io/badge/Spring%20Cloud-2025-green?logo=spring&logoColor=white" alt="Spring Cloud" /> <img src="https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql&logoColor=white" alt="MySQL" /> <img src="https://img.shields.io/badge/Eureka-Discovery-yellowgreen?logo=spring&logoColor=white" alt="Eureka" /> <img src="https://img.shields.io/badge/Gateway-Enabled-orange?logo=springboot&logoColor=white" alt="Gateway" /> <img src="https://img.shields.io/badge/Status-Active-success" alt="Status" /> </div>

---

## 📁 Project Structure

```java
Quiz-App-Microservice/
├── api-gateway/ # Gateway for routing requests
├── question-module/ # Manages quiz questions (CRUD)
├── quiz-module/ # Orchestrates quiz creation and evaluation
└── service-registry/ # Eureka Server for service discovery
```

---

## 🚀 Tech Stack

- **Java 17+ / JDK 21+**
- **Spring Boot 3+**
- **Spring Cloud (2025+)**
- **Spring Data JPA**
- **MySQL Database**
- **Eureka (Service Discovery)**
- **Spring Cloud Gateway**
- **OpenFeign (Service-to-Service Communication)**

---

## 🧩 Microservice Overview

### 1. 🧾 Question Service (`question-module`)
Handles CRUD operations for quiz questions and quiz question selection logic.

#### 🔧 Port
```java
8081
```

#### 🔗 Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/question/add` | Add a new question |
| GET    | `/question/getAll?category={optional}` | Get all questions or filter by category |
| PUT    | `/question/update` | Update an existing question |
| DELETE | `/question/delete/{id}` | Delete a question |
| GET    | `/question/generateQuiz?cat={category}&numQ={count}` | Generate quiz questions by category |
| POST   | `/question/getQuizQuestions` | Get full question details by IDs |
| POST   | `/question/getScore` | Calculate score based on submitted answers |

---

### 2. 📊 Quiz Service (`quiz-module`)
Manages the creation, storage, and evaluation of quizzes using the Question Service.

#### 🔧 Port
```java
8090
```

#### 🔗 Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/quiz/create` | Create a quiz with given category, question count, and title |
| GET    | `/quiz/get/{id}` | Get a quiz by ID with all its questions |
| POST   | `/quiz/submit/{id}` | Submit responses and calculate score |
| DELETE | `/quiz/delete/{id}` | Delete a quiz by ID |

#### 📡 Inter-Service Communication
- Uses **OpenFeign** to call `question-module` for:
  - Generating questions
  - Fetching question details
  - Scoring responses

---

### 3. 🗂️ Service Registry (`service-registry`)
A Eureka server that enables service discovery across microservices.

#### 🔧 Port
```java
8761
```
#### 🔗 Eureka Dashboard
```url
http://localhost:8761/
```

#### 📄 Configuration

```properties
spring.application.name=service-registry
server.port=8761
eureka.instance.hostname=localhost
eureka.client.fetch-registry=false
eureka.client.register-with-eureka=false
```
---
### 4. 🚪 API Gateway (api-gateway)
Acts as the entry point for all requests, routing them to appropriate services.
#### 🔧 Port
```java
8080
```
#### 📄 Configuration

```properties
spring.application.name=api-gateway
server.port=8080

spring.cloud.gateway.server.webflux.discovery.locator.enabled=true
spring.cloud.gateway.server.webflux.discovery.locator.lower-case-service-id=true
```
#### ✅ Features
- 🔁 Dynamic routing using Eureka discovery
- ⚖️ Load balancing between multiple instances
- 🛡️ Easily extendable to add authentication and rate limiting
- 🌐 Centralized entry point for all microservices

---

## 🗃️ Database Configuration Example

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
---
## 🧑‍💻 Author

Made with ❤️ by **Sayan De**

   



