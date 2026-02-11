Smart Procurement & Vendor Management System (SPVMS)
Overview

Smart Procurement & Vendor Management System (SPVMS) is a web-based application designed to streamline procurement processes and manage vendor operations efficiently. The system enables organizations to handle vendor registration, procurement requests, approval workflows, and notification services in a structured and transparent manner.

The project follows Agile methodology and is developed using a microservices-based architecture.

Features

Vendor Registration and Management

Procurement Request Creation and Tracking

Submit and Approval Workflow

Role-Based Access Control

Email Notification System

Audit Logging

RESTful APIs

Microservices Architecture

Tech Stack
Backend

Java 17

Spring Boot

Spring Data JPA

Hibernate

MySQL

Maven

Frontend

React.js / HTML / CSS / JavaScript

Tools & Technologies

Git & GitHub

Postman

IntelliJ IDEA

Agile (Scrum Methodology)
Installation and Setup
Prerequisites

Java 17 or higher

Maven

Node.js and npm

MySQL

Git

Backend Setup

Clone the repository:

git clone <repository-url>


Navigate to backend folder:

cd backend


Configure database in application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/spvms
spring.datasource.username=your_username
spring.datasource.password=your_password


Run the application:

mvn spring-boot:run


Backend will start on:

http://localhost:8080

Frontend Setup

Navigate to frontend folder:

cd frontend


Install dependencies:

npm install


Start the development server:

npm start


Frontend will start on:

http://localhost:3000

Agile Documentation

This project follows Scrum methodology.

Sprint-wise documentation is available in the /docs folder, including:

Sprint Goals

User Stories

Tasks Completed

Implementation Details

Outcomes

API Documentation

REST APIs are developed using Spring Boot.

You can test APIs using Postman.

Main modules include:

Vendor APIs

Procurement APIs

Approval APIs

Notification APIs

Architecture

The system follows a microservices-based architecture where different modules operate independently and communicate via REST APIs.

Key components:

Vendor Service

Procurement Service

Notification Service

Audit Service

License

This project is licensed under the MIT License.

It allows use, modification, and distribution of the software, including for commercial purposes, provided that the original license notice is included. The software is provided without warranty.
