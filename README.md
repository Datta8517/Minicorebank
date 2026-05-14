# MiniCoreBank

A production-style full-stack banking application built using Spring Boot, React, MongoDB, Kafka, Docker, AWS EC2, and Vercel.

## Project Overview

MiniCoreBank is a modern banking backend and frontend system designed to simulate real-world banking workflows such as:

* User Registration & Authentication
* JWT-based Security
* Role-based Authorization (ADMIN / CUSTOMER)
* Account Management
* Fund Transfers
* Deposit & Withdrawal Operations
* Transaction History
* Two-Factor Authentication (2FA)
* Kafka-based Event Processing
* Monitoring with Prometheus & Grafana
* Dockerized Deployment
* Cloud Deployment on AWS EC2 + Vercel

---

# Tech Stack

## Frontend

* React
* Vite
* Material UI (MUI)
* Axios
* React Router

## Backend

* Spring Boot
* Spring Security
* JWT Authentication
* Spring Data MongoDB
* Apache Kafka
* Maven

## Database

* MongoDB Atlas

## Messaging & Monitoring

* Apache Kafka
* Zookeeper
* Kafka UI
* Prometheus
* Grafana

## DevOps & Deployment

* Docker
* Docker Compose
* AWS EC2
* Nginx Reverse Proxy
* SSL using Certbot + DuckDNS
* Vercel

---

# Project Structure

```text
minicorebank/
│
├── minicorebank-ui/                  ← React Frontend
│
├── minicorebank_mongoDB/
│   └── minicorebank/                ← Spring Boot Backend
│
├── docker-compose.yml
└── README.md
```

---

# Features

## Authentication & Security

* JWT Access Token Authentication
* Refresh Token Support
* Role-based Authorization
* Account Lock Mechanism
* JWT Blacklisting
* 2FA PIN Support
* Spring Security Integration

## Banking Operations

* Create Account
* Deposit Money
* Withdraw Money
* Transfer Funds
* View Transaction History
* Admin User Management
* Pending Account Approval

## Kafka Integration

Kafka is used for asynchronous event-driven workflows:

* Transaction Events
* Audit Logging
* Notification/Event Processing
* Future Microservices Scalability

---

# Monitoring Stack

| Tool       | Purpose                 |
| ---------- | ----------------------- |
| Prometheus | Metrics Collection      |
| Grafana    | Visualization Dashboard |
| Kafka UI   | Kafka Monitoring        |

---

# Deployment Architecture

```text
Vercel Frontend (HTTPS)
        ↓
Nginx Reverse Proxy + SSL
        ↓
Spring Boot Backend (Docker)
        ↓
Kafka + MongoDB Atlas
```

---

# Cloud Deployment

## Frontend

Deployed on:

* Vercel

## Backend

Deployed on:

* AWS EC2 Ubuntu Instance

## Domain & SSL

* DuckDNS
* Let's Encrypt SSL
* Nginx Reverse Proxy

---

# Backend API Base URL

```text
https://minicorebank.duckdns.org/minicorebank
```

---

# API Documentation

Swagger UI:

```text
https://minicorebank.duckdns.org/minicorebank/swagger-ui/index.html
```

---

# Monitoring URLs

## Kafka UI

```text
http://<EC2-IP>:8085
```

## Prometheus

```text
http://<EC2-IP>:9090
```

## Grafana

```text
http://<EC2-IP>:3000
```

---

# Local Development Setup

## Clone Repository

```bash
git clone https://github.com/<your-username>/Minicorebank.git
```

---

# Frontend Setup

```bash
cd minicorebank-ui
npm install
npm run dev
```

Create `.env`:

```env
VITE_API_URL=http://localhost:8082/minicorebank
```

---

# Backend Setup

```bash
cd minicorebank_mongoDB/minicorebank
```

## Build Project

```bash
./mvnw clean package
```

## Run Application

```bash
java -jar target/*.jar
```

---

# Docker Deployment

## Start Containers

```bash
docker-compose up -d --build
```

## Stop Containers

```bash
docker-compose down
```

---

# Docker Services

* Spring Boot Backend
* Kafka
* Zookeeper
* Kafka UI
* Prometheus
* Grafana

---

# Security Configuration

The application uses:

* Stateless JWT Authentication
* Spring Security Filter Chain
* Role-based Endpoint Authorization
* CORS Configuration
* Secure Password Encoding

---

# Future Enhancements

* Microservices Architecture
* API Gateway
* Eureka Service Registry
* Notification Service
* CI/CD Pipeline
* Kubernetes Deployment
* Redis Caching
* AI-powered Banking Assistant

---

# Author

Dattatray Jamkhande

---

# License

This project is for educational and portfolio purposes.
