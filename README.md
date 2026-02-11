# Spring Boot Auction System

A real-time auction system built with:

- Spring Boot
- Spring Security (form login + signup)
- Spring WebSocket (STOMP + SockJS)
- MySQL + Spring Data JPA
- Thymeleaf frontend

## Features

- User signup and login.
- View active auctions after authentication.
- Open an auction page and submit bids in real time.
- Current highest bid updates instantly for connected users.
- Validation ensures each bid is higher than current price.

## Run locally

1. Create/start MySQL and ensure credentials in `application.properties` are valid.
2. Run:
   ```bash
   mvn spring-boot:run
   ```
3. Open `http://localhost:8080`.
4. Use seeded user `demo / password` or register a new account on `/signup`.

## Default DB config

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auction_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=password
```

Update these values to match your local environment.
