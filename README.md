# FinVision - Personal Financial Management System

FinVision is a modular and scalable Personal Financial Management System built with Spring Boot and React. It provides a comprehensive solution for managing personal finances with a focus on extensibility and intelligent automation.

## Project Structure

The project is organized into multiple modules:

- `finvision-common`: Shared utilities, configurations, and common components
- `finvision-core`: Core domain models and business logic
- `finvision-api`: REST API layer and controllers
- `finvision-web`: React-based frontend application

## Features

### Core Features
- Account Management
- Transaction Tracking
- Budget Planning
- Financial Reports
- Automated Alerts
- Savings Suggestions

### Technical Features
- Modular Architecture
- Plugin System for Extensions
- RESTful API
- Modern React Frontend
- Spring Security
- JPA/Hibernate for Data Persistence
- OpenAPI Documentation

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- H2 Database (Development)
- Maven
- Lombok
- MapStruct

### Frontend
- React
- TypeScript
- Material-UI
- Redux Toolkit
- React Query

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 18 or higher
- Maven 3.8 or higher
- npm or yarn

### Development Setup

1. Clone the repository:
```bash
git clone https://github.com/livyson/FinVision.git
cd FinVision
```

2. Build the backend:
```bash
mvn clean install
```

3. Start the backend application:
```bash
cd finvision-api
mvn spring-boot:run
```

4. Start the frontend development server:
```bash
cd finvision-web
npm install
npm start
```

The application will be available at:
- Backend API: http://localhost:8080
- Frontend: http://localhost:3000
- API Documentation: http://localhost:8080/swagger-ui.html

## Architecture

### MVC Pattern
- Model: Domain entities and business logic in `finvision-core`
- View: React components in `finvision-web`
- Controller: REST controllers in `finvision-api`

### Module Dependencies
```
finvision-web → finvision-api → finvision-core → finvision-common
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.