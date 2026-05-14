# 🏦 MiniCoreBank — Production-Grade Distributed Banking Backend

> A comprehensive, production-grade core banking system demonstrating distributed microservices architecture, event-driven design, scalability patterns, and enterprise-level security. Built to solve real-world banking challenges at scale.

[![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen?logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-Latest-green?logo=mongodb&logoColor=white)](https://www.mongodb.com/)
[![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-Latest-black?logo=apache-kafka&logoColor=white)](https://kafka.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Containerized-blue?logo=docker&logoColor=white)](https://www.docker.com/)

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Distributed Systems Patterns](#distributed-systems-patterns)
- [Monitoring & Observability](#monitoring--observability)
- [Testing](#testing)
- [Future Enhancements](#future-enhancements)

---

## 🎯 Overview

**MiniCoreBank** is a comprehensive banking backend system designed to demonstrate mastery of:

- **Distributed Systems Architecture** — Microservices, event-driven design, scalability patterns
- **Large-Scale Data Processing** — Handling millions of daily transactions with consistency guarantees
- **Observability & Monitoring** — Real-time metrics, health checks, structured logging
- **Banking Domain Expertise** — Account lifecycle, payment processing, compliance, audit trails
- **Production-Grade Security** — JWT authentication, role-based access control, 2FA, encryption

---

## ✨ Features

### Core Banking Operations
- ✅ **Account Management** — Create, approve, freeze, close customer accounts
- ✅ **Fund Transfers** — Secure fund transfers between accounts with consistency guarantees
- ✅ **Transaction History** — Complete transaction ledger with filtering and pagination
- ✅ **Customer Onboarding** — Simplified KYC and account activation workflows

### Security & Compliance
- ✅ **JWT-based Stateless Authentication** — Scalable token-based auth without server-side sessions
- ✅ **Role-Based Access Control (RBAC)** — Granular permissions (ADMIN, CUSTOMER)
- ✅ **Two-Factor Authentication (2FA)** — PIN-based validation for high-risk operations
- ✅ **Password Hashing** — bcrypt encryption for secure credential storage
- ✅ **Comprehensive Audit Logging** — Every action tracked with timestamp, IP, user, operation type

### Scalability & Performance
- ✅ **Distributed Microservices** — Services designed for independent scaling
- ✅ **Stateless API Design** — Horizontal scaling without session affinity
- ✅ **Connection Pooling** — HikariCP for efficient database connection management
- ✅ **Async Event Processing** — Apache Kafka for decoupled, scalable transaction processing
- ✅ **Pagination Support** — Prevent memory exhaustion on large result sets

### Observability & Monitoring
- ✅ **Spring Boot Actuator** — Health checks, metrics endpoints (/health, /metrics)
- ✅ **Prometheus Integration** — Real-time metric collection (JVM, HTTP, custom business metrics)
- ✅ **Grafana Dashboards** — Visual monitoring of system health, transaction throughput
- ✅ **Structured Logging** — SLF4J + Logback for distributed tracing across services
- ✅ **Kafka UI** — Visual Kafka topic monitoring and message inspection

---

## 🏗️ System Architecture

### Technology Stack

| Component | Technology |
|-----------|-----------|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.5 |
| **Authentication** | JWT + Spring Security |
| **Message Queue** | Apache Kafka |
| **Databases** | MongoDB + Oracle 19c |
| **Monitoring** | Prometheus + Grafana + Spring Boot Actuator |
| **Containerization** | Docker + Docker Compose |
| **Frontend** | React 18 + Vite |
| **Testing** | JUnit + Mockito |

### Architecture Diagram

```
Frontend (React + Vite)
    ↓ (HTTP/REST)
Spring Boot Backend
    ├─ Controllers (REST APIs)
    ├─ Services (Business Logic)
    └─ Data Access (Spring Data)
    ↓
┌─────────────────┬──────────────────┬──────────────────┐
│                 │                  │                  │
MongoDB        Apache Kafka    Spring Boot Actuator
(Document DB)  (Event Stream)   (Metrics/Health)
    │                 │
    └─────────────────┼──────────────────────┐
                      │                      │
                  Prometheus            Grafana
               (Metrics Collection)  (Visualization)
```

---

## 🚀 Quick Start

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Node.js 18+ (for frontend)

### Running with Docker (Recommended)

```bash
cd minicorebank_mongoDB/minicorebank

# Start all services
docker-compose up -d

# Services available at:
# - API: http://localhost:8082/minicorebank
# - Swagger: http://localhost:8082/minicorebank/swagger-ui.html
# - Kafka UI: http://localhost:8085
# - Prometheus: http://localhost:9090
# - Grafana: http://localhost:3000
```

### Local Development

```bash
# Backend
cd minicorebank_mongoDB/minicorebank
mvn clean install
mvn spring-boot:run

# Frontend
cd minicorebank-ui
npm install
npm run dev
```

---

## 📡 API Documentation

### Register & Login

```http
POST /api/auth/register
{
  "username": "john_doe",
  "password": "SecurePassword123",
}

POST /api/auth/login
{
  "username": "john_doe",
  "password": "SecurePassword123"
}
```

### Account Operations

```http
POST /api/accounts/create
Authorization: Bearer {token}
{
  "type": "SAVINGS",
  "initialBalance": 10000.00
}

GET /api/accounts/my-account
Authorization: Bearer {token}

PUT /api/accounts/approve/{accountId}
Authorization: Bearer {adminToken}
```

### Transactions

```http
POST /api/transactions/transfer
Authorization: Bearer {token}
{
  "fromAccountId": "acc_123",
  "toAccountId": "acc_456",
  "amount": 500.00,
  "pin": "1234"
}

GET /api/transactions/account/{accountId}
```

**Full API Docs:** https://minicorebank.duckdns.org/minicorebank/swagger-ui/index.html
---

## 🎯 Distributed Systems Patterns

### 1. Stateless Architecture
- JWT tokens eliminate server-side sessions
- Services scale horizontally without sticky sessions
- Load balancers can route requests freely

### 2. Event-Driven Design
- Transactions publish events to Kafka
- Consumers process asynchronously
- Services are decoupled and independently scalable

### 3. Saga Pattern (Distributed Transactions)
- Fund transfers involve multiple steps: debit → credit → log
- Each step is recorded independently
- Failure handling via compensating transactions

### 4. Distributed Logging & Tracing
- SLF4J structured logs for correlation
- AuditLog captures every user action
- Prometheus metrics enable real-time observability

---

## 📊 Monitoring & Observability

### Spring Boot Actuator

```bash
curl http://localhost:8082/minicorebank/actuator/health
curl http://localhost:8082/minicorebank/actuator/metrics
```

### Prometheus (http://localhost:9090)

Query examples:
```promql
# HTTP request rate
rate(http_server_requests_seconds_count[5m])

# P95 request latency
histogram_quantile(0.95, http_server_requests_seconds_bucket)

# JVM heap memory
jvm_memory_used_bytes{area="heap"}
```

### Grafana (http://localhost:3000)

Default credentials: admin / Datta123

Pre-configured dashboards:
- System Health (JVM, CPU, Memory)
- API Performance (Request rate, latency, errors)
- Transaction Metrics (Transfer success rate, volume)

---

## ✅ Testing

```bash
cd minicorebank_mongoDB/minicorebank

# Run all tests
mvn test

# Run with coverage
mvn test jacoco:report
# View: target/site/jacoco/index.html
```

**Current Coverage:** 90%+

---

## 🚀 Future Enhancements

- [ ] Kubernetes Deployment
- [ ] Load Testing (JMeter/Gatling)
- [ ] Database Sharding
- [ ] API Rate Limiting
- [ ] Eureka Service Registry
- [ ] Service Mesh (Istio)
- [ ] Real-time Notifications (WebSocket)
- [ ] Redis Caching
- [ ] Elasticsearch for Transaction Search
- [ ] CI/CD Pipeline
- [ ] AI-powered Banking Assistant

---

## 🔒 Security Features

- JWT token-based authentication with refresh tokens
- Role-based access control (RBAC)
- Two-factor authentication (2FA) with PIN validation
- Password hashing with bcrypt
- Comprehensive audit logging
- Account status validation (ACTIVE, FROZEN, CLOSED)
- Balance validation before operations
- SQL injection protection via parameterized queries
- CORS configuration for frontend security

---

## 🛠️ Configuration

### MongoDB

```properties
# Local
spring.data.mongodb.uri=mongodb://localhost:27017/minicorebankdb

# Atlas (Cloud)
spring.data.mongodb.uri=mongodb+srv://user:pass@cluster.mongodb.net/minicorebankdb
```

### JWT

```properties
jwt.secret=your-secret-key-min-32-chars-long
jwt.accessExpirationMs=900000      # 15 minutes
jwt.refreshExpirationMs=1209600000 # 14 days
```

### Kafka

```properties
spring.kafka.bootstrap-servers=kafka:29092
spring.kafka.consumer.group-id=minicorebank-group
```

---

## 📝 Project Structure

```
minicorebank/
├── minicorebank_mongoDB/
│   └── minicorebank/
│       ├── src/main/java/com/minicorebank/
│       │   ├── controller/          # REST API endpoints
│       │   ├── service/             # Business logic
│       │   ├── model/               # Data models
│       │   ├── repository/          # Data access layer
│       │   ├── dto/                 # Request/Response DTOs
│       │   ├── kafka/               # Event-driven processing
│       │   ├── config/              # Spring configurations
│       │   └── MiniCoreApplication.java
│       ├── src/test/                # Unit & integration tests
│       ├── pom.xml
│       └── docker-compose.yml
├── minicorebank-ui/                 # React Frontend
└── README.md
```

---

## 🐛 Troubleshooting

### MongoDB Connection Failed
```bash
# Check connection string
mongo "mongodb+srv://user:pass@cluster.mongodb.net/minicorebankdb"

# For Atlas, verify IP whitelist includes your machine
```

### Kafka Connection Issues
```bash
docker-compose logs kafka
docker-compose restart kafka
```

### JWT Token Expired
- Access tokens expire after 15 minutes
- Use refresh token endpoint to get new token
- Clear local storage and re-login

### Port Already in Use
```bash
lsof -i :8082  # Find process
kill -9 <PID>  # Kill process
```

---

## 📚 References

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Apache Kafka Guide](https://kafka.apache.org/documentation/)
- [MongoDB Best Practices](https://docs.mongodb.com/manual/administration/production-checklist/)
- [Prometheus Documentation](https://prometheus.io/docs/)
- [Microservices Patterns](https://microservices.io/patterns/index.html)

---

## 🤝 Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit changes (`git commit -am 'Add feature'`)
4. Push to branch (`git push origin feature/your-feature`)
5. Submit a Pull Request

---

## 📞 Contact

- **Email:** dattatrayjamkhande23@gmail.com
- **LinkedIn:** [Your LinkedIn](https://linkedin.com/in/your-profile)
- **GitHub:** [Your GitHub](https://github.com/yourusername)

---

## 📄 License

This project is for educational and portfolio purposes.

---

**Made with ❤️ by Dattatray Jamkhande**

⭐ **If you found this project helpful, please consider giving it a star!**

---
