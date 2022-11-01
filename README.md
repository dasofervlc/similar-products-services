# SIMILAR-PRODUCT-SERVICE

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Know defects](#Know-defects)
* [Things to improve](#Things-to-improve)


## General info
The main objective of this rest Microservices is received products Ids
and retrieve a list of similar products. 
* Develop in Java +Spring with reactor
* Implemented a Spring application using DDD.
* Organized layers with the help of Hexagonal Architecture.
* Added Swagger UI that allow us to interact with our API specification and exercise the endpoints.

## Technologies
Project is created with:
* Java
* Spring boot
* Reactive programing (reactor)
* Swagger
* Maven

## :computer: How tu use

* Access to swagger-ui:
```
http://localhost:5000/swagger-ui/index.html
```

* Access to api-docs:
```
http://localhost:5000/v3/api-docs/
```

## :memo: Know defects
- The application is not full reactive because the contract specified List of Products
- Reactor API have a defect transforming a List of String in Flux of strings https://github.com/spring-projects/spring-framework/issues/22662

## :pushpin: Things to improve

- Improve coverage
- Create database unit test using H2, improve the coverage testing and add Integration test starting from a mockServer
- Improve the logic: Is not checked if similar products have repeated products, etc..
- Improve application logging and exception handle