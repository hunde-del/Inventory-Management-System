# Inventory Management System (Spring Boot + MySQL + HTML/CSS/JS)

A production-style full-stack Inventory Management System with role-based authentication, product management, stock tracking, dashboard metrics, and inventory reports.

## Backend

### Architecture
- MVC + layered architecture:
  - `controller` - REST endpoints
  - `service` - business logic
  - `repository` - data access with JPA
  - `dto` - request/response payload models
  - `exception` - centralized global error handling

### Security
- Spring Security with session-based authentication
- BCrypt password hashing
- Roles:
  - `ROLE_ADMIN`: full access, including delete and register user
  - `ROLE_MANAGER`: CRUD except delete

### Main features implemented
- Authentication
  - Login (`/api/auth/login`)
  - Logout (`/api/auth/logout`)
  - Current user (`/api/auth/me`)
  - Register user (`/api/auth/register`, admin-only)
- Products
  - Add/Edit/Delete/Get/List + filter by category
  - Low-stock endpoint
  - Negative stock prevented by validation and business rules
- Dashboard
  - Total products
  - Low-stock item count
  - Recently added products
- Reports
  - Inventory listing
  - Category filter
  - Summary: total item quantity and total inventory value

## Frontend

Static pages served by Spring Boot:
- `login.html`
- `dashboard.html`
- `products.html`
- `product-form.html`

The frontend uses `fetch` API with cookies (`credentials: include`) to call backend endpoints and shows success/error messages.

## Database

### Tables
1. `users`
   - `id` (PK)
   - `username`
   - `password`
   - `role`

2. `products`
   - `id` (PK)
   - `name`
   - `category`
   - `quantity`
   - `price`
   - `created_at`

SQL schema file:
- `src/main/resources/db/schema.sql`

## Setup Instructions

## 1) Prerequisites
- Java 17+
- Maven 3.9+
- MySQL 8+

## 2) Configure database
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/inventory_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

## 3) Start the app
```bash
mvn spring-boot:run
```

## 4) Open in browser
- `http://localhost:8080/login.html`

Default seeded users:
- Admin: `admin / admin123`
- Manager: `manager / manager123`

## API Endpoint List

### Auth
- `POST /api/auth/login`
- `POST /api/auth/logout`
- `GET /api/auth/me`
- `POST /api/auth/register` (admin only)

### Products
- `POST /api/products`
- `GET /api/products`
- `GET /api/products?category={name}`
- `GET /api/products/{id}`
- `PUT /api/products/{id}`
- `DELETE /api/products/{id}` (admin only)
- `GET /api/products/low-stock`

### Dashboard
- `GET /api/dashboard`

### Reports
- `GET /api/reports/inventory`
- `GET /api/reports/inventory?category={name}`

## Notes
- Low-stock threshold defaults to 10 and can be changed via:
```properties
inventory.low-stock-threshold=10
```
- Global exception handling ensures consistent JSON error responses.
