# Spring Boot Request Flow

This document explains the full **HTTP request flow inside Spring /
Spring Boot**, from the moment a client sends a request until a response
is returned.

##  1. Client Sends an HTTP Request

Example requests: - `GET /users` - `POST /login` - `PUT /products/5`

The request arrives at the Spring Boot embedded server.

##  2. Embedded Server Receives the Request

Spring Boot uses an embedded servlet container: - Tomcat (default) -
Jetty - Undertow

## 3. Filter Chain

Filters run before and after request processing.

## 4. DispatcherServlet (The Heart of Spring MVC)

Central controller that routes all Spring MVC requests.

## 5. Handler Mapping

DispatcherServlet checks which controller should handle the request.

## 6. HandlerAdapter

Executes the matched controller.

## 7. Controller Method Executes

Spring handles: - argument resolving\
- validation\
- request body parsing

## 8. Service Layer

Business logic lives here.

## 9. Repository Layer

Interacts with the database using JPA / JDBC / Hibernate.

## 10. Response Returned

## 11. View Resolver (MVC only)

## 12. HttpMessageConverter

Converts response into JSON / XML.

## 13. Post-filters

## 14. Response Sent Back to Client

# Full Request Flow Diagram

    Client
       ↓
    Embedded Server
       ↓
    Filter Chain
       ↓
    DispatcherServlet
       ↓
    HandlerMapping
       ↓
    HandlerAdapter
       ↓
    Controller
       ↓
    Service
       ↓
    Repository
       ↓
    Database
       ↑
    Response
       ↑
    HttpMessageConverter
       ↑
    DispatcherServlet
       ↑
    Filters
       ↑
    Client


## Reference Articles
[Filter vs Interceptors](https://medium.com/@rhom159/filters-vs-interceptors-in-spring-a-simple-guide-for-easy-understanding-70f5e397fa32)
[Thread vs ThreadLocal](https://medium.com/@sachinkg12/understanding-threadlocal-vs-thread-in-java-a908b5390207)
[MDC](https://medium.com/@sudacgb/enhancing-logging-in-spring-boot-with-mapped-diagnostic-context-mdc-a-step-by-step-tutorial-0a57b0304dd3)

