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

### Authentication
```
POST /api/v1/auth/login
POST /api/v1/auth/register
```

### Customer Management
```
GET    /api/v1/customers          # Get all customers
GET    /api/v1/customers/{id}     # Get customer by ID
POST   /api/v1/customers          # Create new customer
PUT    /api/v1/customers/{id}     # Update customer
DELETE /api/v1/customers/{id}     # Delete customer
```

### Monitoring
```
GET /actuator/health              # Health check
GET /actuator/custom-info         # Detailed application information
GET /actuator/metrics             # Application metrics
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

### Example Usage

#### Get Application Information
```bash
curl -X GET http://localhost:8080/actuator/custom-info
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
    "password": "password"
  }'
```

### Use JWT token
```bash
curl -X GET http://localhost:8080/api/v1/customers \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

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