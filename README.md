# Computer and Keys REST API

A production-ready REST service for managing computers and SSH keys. Built with Java 11, Spring Boot, and MySQL.

## Features

- **Computers Management**: CRUD operations with JSON/XML support
- **SSH Keys Management**: Secure SSH key for build servers
- **Content Negotiation**: Automatic format selection based on Accept headers
- **OpenAPI Documentation**: Interactive API docs via Swagger UI
- **Input Validation**: Comprehensive validation with meaningful error messages
- **Production Ready**: Configured for deployment with environment variables

## Table of Contents

- [Quick Start](#quick-start)
- [API Endpoints](#api-endpoints)
- [Configuration](#configuration)
- [Documentation](#documentation)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)

## Quick Start

### Prerequisites

- Java 11 
- Maven 3.9+
- Aiven MySQL account (or any MySQL 8.0+)
- Docker (for containerized deployment)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/LucyFerrr/ComputerAndKeys.git
cd ComputerAndKeys
```

2. **Configure database connection**

This project uses **Aiven MySQL**. Get your connection details from Aiven console.

Create `.env` file (add to `.gitignore`):
```
SPRING_DATASOURCE_URL=jdbc:mysql://mysql-test123.aivencloud.com:12345/defaultdb?ssl-mode=REQUIRED
SPRING_DATASOURCE_USERNAME=avnadmin
SPRING_DATASOURCE_PASSWORD=your_secured_password
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQL8Dialect
```

3. **Create required table in MySQL Workbench**

Connect to your Aiven database and run:
```
CREATE TABLE computer_colors (
  computer_id BIGINT NOT NULL,
  color VARCHAR(255),
  PRIMARY KEY (computer_id, color),
  CONSTRAINT fk_computer FOREIGN KEY (computer_id) 
    REFERENCES computers(id) ON DELETE CASCADE
);
```

4. **Build and run**
```
# Build
mvn clean install

# Run locally
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### Verify Installation

```
# View API documentation
open http://localhost:8080/swagger-ui.html
```

##  API Endpoints

See the API Documentation for the detailed information.

## Configuration

See the Project Documentation for the detailed development setup.

## Documentation

- **API Documentation**: https://computerandkeys.onrender.com/swagger-ui/index.html
- **Project Documentation**: https://personalprojects-2.gitbook.io/computers-and-keys/readme
- **Test Documentation**: https://personalprojects-2.gitbook.io/computers-and-keys/test-documentation
- **Deployment Guide**: https://personalprojects-2.gitbook.io/computers-and-keys/deployment-documentation

## Testing

### Run All Tests

```
mvn test
```

### Run Specific Test Class

```
mvn test -Dtest=ComputerServiceImplTest
```

### Manual Testing

Use Postman collection or cURL commands.

## Deployment

### Local Deployment

```
# Build JAR
mvn clean package

# Run JAR
java -jar target/computer-keys-api-1.0.0.jar
```

### Cloud Deployment (Render)

See Deployment Guide for detailed instructions.

## Known Issues

1. **Computer Colors Table**: May need manual creation on some MySQL versions
   ```
   CREATE TABLE computer_colors (
     computer_id BIGINT NOT NULL,
     color VARCHAR(255),
     PRIMARY KEY (computer_id, color),
     CONSTRAINT fk_computer FOREIGN KEY (computer_id) 
       REFERENCES computers(id)
   );
   ```

## API Behavior Notes

- **403 vs 404**: Returns `403 Forbidden` when maker exists but model parameter is missing; returns `404 Not Found` when maker doesn't exist
- **Content Negotiation**: Computers endpoint supports both JSON and XML based on `Accept` header quality values
- **SSH Key Validation**: 
  - `ssh-rsa`: Minimum 300 characters
  - `ssh-ed25519`: 40-100 characters
