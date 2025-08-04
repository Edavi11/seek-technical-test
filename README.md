# Customer Service API

## 📋 Description

REST API for customer management developed with Spring Boot, providing complete CRUD operations for customer information handling. The application includes JWT authentication, Swagger documentation, and is optimized for AWS ECS deployment.

## 🚀 Features

### ✅ Main Features
- **Complete customer CRUD** (Create, Read, Update, Delete)
- **JWT authentication** with secure tokens
- **Data validation** with Bean Validation
- **Automatic documentation** with Swagger/OpenAPI
- **Health checks** for monitoring
- **Structured logging** with different levels
- **Database migrations** with Flyway

### 🛡️ Security
- **Spring Security** integrated
- **JWT tokens** for authentication
- **Robust input validation**
- **CORS configured** for APIs
- **Automatic security headers**

### 📊 Monitoring and Observability
- **Actuator endpoints** for health checks and application information
- **Custom application info endpoint** with detailed system information
- **CloudWatch metrics** integrated
- **Centralized logging** in CloudWatch
- **Automatic health checks** in ECS

### 🏗️ Architecture
- **Microservices** with Spring Boot
- **Relational database** (MySQL)
- **Containerization** with Docker
- **Serverless deployment** in AWS Fargate
- **Automatic load balancing**
- **CPU-based auto-scaling**

### 🏗️ Infrastructure Diagram

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              CUSTOMER SERVICE API                               │
│                              Infrastructure Overview                            │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                                    USERS                                        │
│                              👤 External Users                                  │
│                                    │                                            │
│                                    ▼                                            │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                              LOAD BALANCER LAYER                               │
│                    AWS Application Load Balancer (ALB)                         │
│                              Port 80/443 (HTTP/HTTPS)                          │
│                                    │                                            │
│                                    ▼                                            │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                              CONTAINER LAYER                                   │
│                           ECS Fargate Cluster                                  │
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                        Customer Service                                 │   │
│  │                                                                         │   │
│  │  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐   │   │
│  │  │   Task 1    │  │   Task 2    │  │   Task 3    │  │   Task N    │   │   │
│  │  │  Port 8080  │  │  Port 8080  │  │  Port 8080  │  │  Port 8080  │   │   │
│  │  │             │  │             │  │             │  │             │   │   │
│  │  │ Spring Boot │  │ Spring Boot │  │ Spring Boot │  │ Spring Boot │   │   │
│  │  │   Docker    │  │   Docker    │  │   Docker    │  │   Docker    │   │   │
│  │  └─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘   │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              DATA LAYER                                        │
│                        AWS RDS MySQL Database                                  │
│                              customer_db                                       │
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                           Database Schema                                │   │
│  │  • users table                                                           │   │
│  │  • customers table                                                       │   │
│  │  • Flyway migrations                                                     │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                              CI/CD PIPELINE                                    │
│                                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐            │
│  │   GitHub Repo   │───▶│ GitHub Actions  │───▶│  Docker Hub     │            │
│  │                 │    │                 │    │                 │            │
│  │ Edavi11/        │    │ • Build         │    │ edavila11/      │            │
│  │ seek-technical- │    │ • Test          │    │ customer-       │            │
│  │ test            │    │ • Deploy        │    │ service         │            │
│  └─────────────────┘    └─────────────────┘    └─────────────────┘            │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                              SECURITY LAYER                                    │
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                    AWS Secrets Manager                                  │   │
│  │  • Database Password                                                    │   │
│  │  • JWT Secret                                                           │   │
│  │  • Application Secrets                                                  │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                        IAM User                                          │   │
│  │  • github-actions-user                                                   │   │
│  │  • ECS Full Access                                                       │   │
│  │  • Least Privilege Principle                                             │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                              MONITORING LAYER                                  │
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                    AWS CloudWatch                                        │   │
│  │  • Application Logs                                                      │   │
│  │  • Performance Metrics                                                   │   │
│  │  • Error Tracking                                                        │   │
│  │  • Auto Scaling Triggers                                                 │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
│                                                                                 │
│  ┌─────────────────────────────────────────────────────────────────────────┐   │
│  │                Spring Boot Actuator                                      │   │
│  │  • /actuator/health - Health Check                                       │   │
│  │  • /actuator/custom-info - Application Info                              │   │
│  │  • /actuator/metrics - Performance Metrics                               │   │
│  │  • /actuator/prometheus - Prometheus Metrics                             │   │
│  └─────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                              DATA FLOW                                         │
│                                                                                 │
│  1. Users → ALB → ECS Tasks                                                    │
│  2. ECS Tasks → RDS Database                                                   │
│  3. GitHub → Actions → Docker Hub → ECS                                        │
│  4. ECS → CloudWatch (Logs & Metrics)                                          │
│  5. ECS → Secrets Manager (Credentials)                                        │
│  6. Auto Scaling based on CPU utilization                                      │
└─────────────────────────────────────────────────────────────────────────────────┘
```

#### **Infrastructure Components:**

**🌐 Frontend Layer:**
- **Application Load Balancer (ALB)**: Distributes traffic across ECS tasks
- **Auto Scaling**: Automatically adjusts task count based on CPU utilization

**🐳 Container Layer:**
- **ECS Fargate**: Serverless container orchestration
- **Docker Images**: Built from GitHub Actions and pushed to Docker Hub
- **Multiple Tasks**: For high availability and load distribution

**🗄️ Data Layer:**
- **AWS RDS MySQL**: Managed relational database
- **Flyway Migrations**: Database schema versioning

**🔒 Security Layer:**
- **IAM Roles**: Least privilege access for ECS tasks
- **Secrets Manager**: Secure storage for sensitive data
- **JWT Authentication**: Stateless authentication tokens

**📊 Monitoring Layer:**
- **CloudWatch**: Centralized logging and metrics
- **Spring Boot Actuator**: Application health and info endpoints
- **Custom Info Endpoint**: Detailed application information
- **Prometheus Metrics**: Standard metrics format for monitoring systems

**🔄 CI/CD Pipeline:**
- **GitHub Actions**: Automated build, test, and deployment
- **Docker Hub**: Container image registry
- **AWS ECS**: Automated deployment and scaling

## 🛠️ Technologies Used

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.3.13** - Main framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **Hibernate** - ORM
- **Flyway** - Database migrations
- **JWT** - Authentication tokens
- **Swagger/OpenAPI** - API documentation

### Database
- **MySQL 8.0** - Relational database
- **AWS RDS** - Managed database

### Infrastructure
- **Docker** - Containerization
- **AWS ECS Fargate** - Container orchestration
- **AWS Application Load Balancer** - Load balancing
- **AWS CloudWatch** - Monitoring and logging
- **AWS Secrets Manager** - Secrets management

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/seek/test/seek_test/
│   │   ├── controller/          # REST controllers
│   │   ├── service/            # Business logic
│   │   ├── repository/         # Data access
│   │   ├── model/              # JPA entities
│   │   ├── dto/                # Transfer objects
│   │   ├── config/             # Configurations
│   │   ├── security/           # Security configuration
│   │   └── exception/          # Exception handling
│   └── resources/
│       ├── application.properties      # Base configuration
│       ├── application-dev.properties  # Development configuration
│       ├── application-prod.properties # Production configuration
│       └── db/migration/       # Flyway migrations
└── test/                       # Unit and integration tests
```

## 🚀 Installation and Configuration

### Prerequisites
- **Java 17** or higher
- **Maven 3.6** or higher
- **Docker** (optional, for containerization)
- **MySQL 8.0** (for local development)

### 1. Clone the repository
```bash
git clone https://github.com/Edavi11/seek-technical-test.git
cd seek-technical-test
```

### 2. Configure environment variables
```bash
# Copy the example file
cp env.example .env

# Edit variables according to your environment
nano .env
```

### 3. Configure database
```sql
-- Create database
CREATE DATABASE customer_db;

-- Create user (optional)
CREATE USER 'customer_user'@'%' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON customer_db.* TO 'customer_user'@'%';
FLUSH PRIVILEGES;
```

### 4. Run the application

#### Option A: Run locally
```bash
# Compile the project
mvn clean compile

# Run with development profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Option B: Run with Docker
```bash
# Build the image
docker build -t customer-service:dev .

# Run the container
docker run -d \
  --name customer-service \
  -p 8080:8080 \
  --env-file .env \
  customer-service:dev
```

## 🔧 Environment Variables Configuration

### Required Variables

#### Database
```env
# MySQL connection URL
DB_URL=jdbc:mysql://localhost:3306/customer_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

# Database credentials
DB_USERNAME=customer_user
DB_PASSWORD=your_password

# Database driver
DB_DRIVER=com.mysql.cj.jdbc.Driver
```

#### JWT Authentication
```env
# Secret key for JWT signing (minimum 256 bits recommended)
JWT_SECRET=your_very_long_and_secure_secret_key_12345678901234567890

# Token expiration time in milliseconds (24 hours default)
JWT_EXPIRATION=86400000
```

#### Application Configuration
```env
# Application port
SERVER_PORT=8080

# Spring Boot profile (dev, prod, docker)
SPRING_PROFILES_ACTIVE=dev
```

#### AWS (for production)
```env
# AWS region
AWS_REGION=us-east-1

# CloudWatch metrics namespace
AWS_CLOUDWATCH_NAMESPACE=CustomerManagementService
```

#### Logging
```env
# Application logging level
LOGGING_LEVEL_COM_SEEK_TEST_SEEK_TEST=DEBUG

# Spring Security logging level
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=DEBUG

# Hibernate SQL logging level
LOGGING_LEVEL_ORG_HIBERNATE_SQL=DEBUG
```

## 📚 API Endpoints

### 🔐 Authentication
```
POST /api/v1/auth/login           # Authenticate user and get JWT token
```

### 👥 User Management
```
GET    /api/v1/users              # Get all users (active and inactive)
POST   /api/v1/users              # Create a new user
PUT    /api/v1/users/{id}/activate    # Activate a user
PUT    /api/v1/users/{id}/deactivate  # Deactivate a user
PUT    /api/v1/users/{id}/password    # Change user password
DELETE /api/v1/users/{id}         # Delete a user completely
```

### 👤 Customer Management
```
GET    /api/v1/customers?page=1&size=10  # Get customers with pagination
GET    /api/v1/customers/{id}     # Get customer by ID
GET    /api/v1/customers/metrics  # Get customer metrics (age statistics)
POST   /api/v1/customers          # Create a new customer
PUT    /api/v1/customers/{id}     # Update customer data
DELETE /api/v1/customers/{id}     # Delete a customer
```

### 📊 Monitoring & Health
```
GET /actuator/health              # Health check
GET /actuator/custom-info         # Detailed application information
GET /actuator/metrics             # Application metrics
GET /actuator/prometheus          # Prometheus metrics
```

### 📖 Documentation
```
GET /swagger-ui.html              # Swagger interface
GET /api-docs                     # OpenAPI specification
```

## 🔄 API Usage Flow Example

### Step 1: Authentication
First, you need to authenticate to get a JWT token:

```bash
# Example Login to get JWT token
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Step 2: Use JWT Token for API Calls
Once you have the JWT token, use it in the Authorization header for all subsequent requests:

```bash
# Set your JWT token
JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# Get all customers (paginated)
curl -X GET "http://localhost:8080/api/v1/customers?page=1&size=10" \
  -H "Authorization: Bearer $JWT_TOKEN"

# Get customer by ID
curl -X GET "http://localhost:8080/api/v1/customers/1" \
  -H "Authorization: Bearer $JWT_TOKEN"

# Create a new customer
curl -X POST http://localhost:8080/api/v1/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "age": 33,
    "birthDate": "1992-08-03"
  }'

# Update customer
curl -X PUT http://localhost:8080/api/v1/customers/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -d '{
    "firstName": "John Updated",
    "lastName": "Doe",
    "age": 34,
    "birthDate": "1992-08-03"
  }'

# Get customer metrics
curl -X GET http://localhost:8080/api/v1/customers/metrics \
  -H "Authorization: Bearer $JWT_TOKEN"

# Delete customer
curl -X DELETE http://localhost:8080/api/v1/customers/1 \
  -H "Authorization: Bearer $JWT_TOKEN"
```

### Step 3: User Management Examples

```bash
# Get all users
curl -X GET http://localhost:8080/api/v1/users \
  -H "Authorization: Bearer $JWT_TOKEN"

# Get only active users
curl -X GET http://localhost:8080/api/v1/users/active \
  -H "Authorization: Bearer $JWT_TOKEN"

# Create a new user
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -d '{
    "username": "newuser",
    "password": "securepassword123"
  }'

# Change user password
curl -X PUT http://localhost:8080/api/v1/users/2/password \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -d '{
    "currentPassword": "oldpassword",
    "newPassword": "newpassword123",
    "confirmPassword": "newpassword123"
  }'

# Activate a user
curl -X PUT http://localhost:8080/api/v1/users/2/activate \
  -H "Authorization: Bearer $JWT_TOKEN"

# Deactivate a user
curl -X PUT http://localhost:8080/api/v1/users/2/deactivate \
  -H "Authorization: Bearer $JWT_TOKEN"

# Delete a user
curl -X DELETE http://localhost:8080/api/v1/users/2 \
  -H "Authorization: Bearer $JWT_TOKEN"
```

### 📝 Important Notes

1. **Authentication Required**: All endpoints except `/api/v1/auth/login` require JWT authentication
2. **Pagination**: Customer listing uses 1-based pagination (page 1, not 0)
3. **Validation**: All inputs are validated for data integrity and business rules
4. **Error Handling**: Comprehensive error responses with appropriate HTTP status codes
5. **Metrics Endpoint**: Provides statistical analysis of customer ages (mean, median, standard deviation, etc.)

### Monitoring
```
GET /actuator/health              # Health check
GET /actuator/custom-info         # Detailed application information
GET /actuator/metrics             # Application metrics
GET /actuator/prometheus          # Prometheus metrics
```

### Documentation
```
GET /swagger-ui.html              # Swagger interface
GET /api-docs                     # OpenAPI specification
```

### Application Information
The application provides detailed information through the Actuator endpoint:

#### `/actuator/custom-info` (Actuator Endpoint)
- **Purpose**: Standard Spring Boot Actuator endpoint for application monitoring
- **Response**: Detailed application information including system details, memory usage, and configuration
- **Access**: Requires authentication in production
- **Documentation**: Follows Spring Boot Actuator standards

The endpoint returns structured information including:
- **Application details**: Name, version, author, technology stack
- **System information**: Java version, OS details, timezone
- **Memory usage**: Total, free, used, and maximum memory
- **Application configuration**: Active profiles, server port, context path
- **Available endpoints**: List of all API endpoints

### Prometheus Metrics

The application exposes Prometheus-compatible metrics for monitoring and observability:

#### `/actuator/prometheus` (Prometheus Endpoint)
- **Purpose**: Exposes application metrics in Prometheus format
- **Format**: Text-based metrics with labels and values
- **Access**: Requires authentication in production
- **Use Case**: Integration with monitoring systems like Prometheus, Grafana, or CloudWatch

#### **Available Metrics:**
- **JVM Metrics**: Memory usage, garbage collection, thread count
- **Application Metrics**: HTTP requests, response times, error rates
- **Custom Metrics**: Business-specific metrics and counters
- **System Metrics**: CPU usage, disk I/O, network statistics

#### **Example Metrics Output:**
```
# HELP jvm_memory_used_bytes The amount of used memory
# TYPE jvm_memory_used_bytes gauge
jvm_memory_used_bytes{area="heap",id="PS Survivor Space"} 1.048576E7

# HELP http_server_requests_seconds Duration of HTTP server request handling
# TYPE http_server_requests_seconds histogram
http_server_requests_seconds_bucket{exception="None",method="GET",outcome="SUCCESS",status="200",uri="/actuator/health",le="0.1"} 1.0

# HELP application_custom_metric Custom business metric
# TYPE application_custom_metric counter
application_custom_metric{operation="customer_created"} 15.0
```

### Example Usage

#### Get Application Information
```bash
curl -X GET http://localhost:8080/actuator/custom-info
```

#### Get Prometheus Metrics
```bash
curl -X GET http://localhost:8080/actuator/prometheus
```

#### Example Response
```json
{
  "app": {
    "name": "Customer Service API",
    "description": "Spring Boot REST API for Customer Management",
    "version": "1.0.0",
    "author": "Erick Avila",
    "email": "erickdavila11@gmail.com",
    "technology": "Spring Boot 3.3.13",
    "javaVersion": "17",
    "database": "MySQL/AWS RDS",
    "deployment": "AWS ECS Fargate",
    "startupTime": "2025-08-04T00:56:40.132"
  },
  "system": {
    "javaVersion": "17.0.2",
    "javaVendor": "Oracle Corporation",
    "osName": "Windows 10",
    "osVersion": "10.0",
    "userTimezone": "America/New_York",
    "userHome": "C:\\Users\\erick",
    "userDir": "C:\\Users\\erick\\OneDrive\\Desktop\\Erick\\Proyectos\\Prueba Tecnica Seek\\seek_test"
  },
  "memory": {
    "totalMemory": "256.0 MB",
    "freeMemory": "180.5 MB",
    "usedMemory": "75.5 MB",
    "maxMemory": "4.0 GB",
    "availableProcessors": 8
  },
  "application": {
    "activeProfiles": "dev",
    "serverPort": "8080",
    "contextPath": "/",
    "timestamp": "2025-08-04T00:56:40.132"
  },
  "endpoints": {
    "health": "/actuator/health",
    "metrics": "/actuator/metrics",
    "prometheus": "/actuator/prometheus",
    "swagger": "/swagger-ui.html",
    "apiDocs": "/api-docs",
    "customers": "/api/v1/customers",
    "users": "/api/v1/users",
    "auth": "/api/v1/auth/login"
  }
}
```

## 🔐 Authentication

### Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Use JWT token
```bash
# Set your JWT token
JWT_TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# Use in API calls
curl -X GET http://localhost:8080/api/v1/customers \
  -H "Authorization: Bearer $JWT_TOKEN"
```

### Important Notes
- **Authentication Required**: All endpoints except `/api/v1/auth/login` require JWT authentication
- **Token Format**: Include `Bearer ` prefix before the JWT token
- **Token Expiration**: JWT tokens expire after 24 hours by default

## 🐳 Docker Deployment

### Build image
```bash
docker build -t customer-service:latest .
```

### Run container
```bash
docker run -d \
  --name customer-service \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e DB_URL=jdbc:mysql://your-rds-endpoint:3306/customer_db \
  -e DB_USERNAME=customer_user \
  -e DB_PASSWORD=your_password \
  -e JWT_SECRET=your_secret_key \
  customer-service:latest
```

## ☁️ AWS ECS Deployment

### 1. Push image to Docker Hub
```bash
# Tag image
docker tag customer-service:latest edavila11/customer-service:latest

# Push to Docker Hub
docker push edavila11/customer-service:latest
```

### 2. Create ECS cluster
```bash
aws ecs create-cluster --cluster-name customer-service-cluster
```

### 3. Create task definition
```bash
aws ecs register-task-definition --cli-input-json file://task-definition.json
```

### 4. Create service
```bash
aws ecs create-service \
  --cluster customer-service-cluster \
  --service-name customer-service \
  --task-definition customer-service-task:1 \
  --desired-count 2 \
  --launch-type FARGATE \
  --network-configuration "awsvpcConfiguration={subnets=[subnet-xxx,subnet-yyy],securityGroups=[sg-xxx],assignPublicIp=ENABLED}"
```

## 🧪 Testing

### Test Structure
The project includes unit testing with the following structure:
- **Unit Tests**: Test individual service components in isolation
- **Service Layer Tests**: Comprehensive testing of business logic
- **Validation Tests**: Test data validation and business rules
- **Pagination Tests**: Test pagination functionality
- **JWT Tests**: Test authentication and token handling

### Available Test Classes
- `CustomerServiceTest.java` - Core customer service functionality
- `CustomerServiceValidationTest.java` - Data validation tests
- `CustomerServicePaginationTest.java` - Pagination and filtering tests
- `UserServiceTest.java` - User management tests
- `JwtServiceTest.java` - JWT token generation and validation tests
- `SeekTestApplicationTests.java` - Spring context loading test

### Running Tests

#### Run all tests
```bash
mvn test
```

#### Run specific test class
```bash
# Run customer service tests
mvn test -Dtest=CustomerServiceTest

# Run validation tests
mvn test -Dtest=CustomerServiceValidationTest

# Run pagination tests
mvn test -Dtest=CustomerServicePaginationTest

# Run JWT tests
mvn test -Dtest=JwtServiceTest

# Run user service tests
mvn test -Dtest=UserServiceTest
```

#### Run tests with specific profile
```bash
mvn test -Dspring.profiles.active=test
```

#### Run tests with verbose output
```bash
mvn test -Dspring.profiles.active=test -X
```

### Test Coverage

#### Generate coverage report
```bash
mvn jacoco:report
```

#### View coverage report
```bash
# Open in browser (macOS/Linux)
open target/site/jacoco/index.html

# Open in browser (Windows)
start target/site/jacoco/index.html

# Or navigate to target/site/jacoco/index.html manually
```

#### Coverage goals
- **Line Coverage**: > 80%
- **Branch Coverage**: > 70%
- **Method Coverage**: > 85%

### Test Configuration

#### Test Database
Tests use an in-memory H2 database for fast execution:
```properties
# Test profile configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
```

#### Test Data
Test data is automatically loaded using:
- `@TestPropertySource` annotations
- `@DirtiesContext` for clean test isolation
- In-memory database with test data setup

### Test Examples

#### Customer Service Test
```java
@Test
void shouldCreateCustomer() {
    // Test customer creation
    CustomerDto customerDto = new CustomerDto();
    customerDto.setFirstName("John");
    customerDto.setLastName("Doe");
    customerDto.setAge(30);
    
    CustomerDto result = customerService.createCustomer(customerDto);
    
    assertThat(result).isNotNull();
    assertThat(result.getId()).isNotNull();
    assertThat(result.getFirstName()).isEqualTo("John");
}
```

#### JWT Service Test
```java
@Test
void shouldGenerateValidToken() {
    // Test JWT token generation
    String token = jwtService.generateToken("testuser");
    
    assertThat(token).isNotNull();
    assertThat(jwtService.validateToken(token)).isTrue();
}
```

### Future Test Improvements
- **Integration Tests**: Add tests for controller layer
- **End-to-End Tests**: Add complete workflow tests
- **API Tests**: Add REST API endpoint tests
- **Performance Tests**: Add load and stress tests

## 📊 Monitoring

### Health Checks
```bash
# Check application status
curl http://localhost:8080/actuator/health

# Check database status
curl http://localhost:8080/actuator/health/db
```

### Metrics
```bash
# Get application metrics
curl http://localhost:8080/actuator/metrics

# Specific metrics
curl http://localhost:8080/actuator/metrics/jvm.memory.used
```

## 🔧 Profile Configuration

### Development (dev)
- Detailed logging
- Swagger enabled
- Database schema validation
- Development configuration

### Production (prod)
- Minimal logging
- Swagger disabled
- Performance optimizations
- Production configuration

### Docker
- Container-optimized configuration
- Structured logging
- Health checks configured

## 🚨 Troubleshooting

### Common Issues

#### Database connection error
```bash
# Check if MySQL is running
sudo systemctl status mysql

# Verify credentials
mysql -u customer_user -p customer_db
```

#### Port already in use
```bash
# Check what process is using port 8080
lsof -i :8080

# Change port in .env
SERVER_PORT=8081
```

#### JWT error
```bash
# Check if JWT_SECRET is configured
echo $JWT_SECRET

# Generate new secret key
openssl rand -hex 32
```

## 📝 Logs

### View application logs
```bash
# Spring Boot logs
tail -f logs/application.log

# Docker logs
docker logs customer-service

# AWS ECS logs
aws logs describe-log-streams --log-group-name /ecs/customer-service-task
```

## 🤝 Contributing

1. Fork the project
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **Erick Davila** - *Initial development* - [Edavi11](https://github.com/Edavi11)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- AWS for cloud infrastructure
- Java developer community

## 📞 Support

For technical support or questions:
- 📧 Email: erickdavila11@gmail.com
- 🐛 Issues: [GitHub Issues](https://github.com/Edavi11/customer-service-api/issues)
- 📖 Documentation: [Project Wiki](https://github.com/Edavi11/seek-technical-test/wiki)

---

**Thank you for using Customer Service API! 🚀** 