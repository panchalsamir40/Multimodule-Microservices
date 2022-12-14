

# Microservices WIP
## Overview
- A maven multimodule project
- Java 17
- Check spring.datasource.username/password in app.properties
- Create a schema name microservices
- This is a spring based maven multimodule project that deals with products, order and inventory services. 
### YOUTUBE
https://www.youtube.com/watch?v=lh1oQHXVSc0&list=PLSVW22jAG8pBnhAdq9S8BpLnZ0_jVBj0c&ab_channel=ProgrammingTechie



## SQL Queries Used 
```
SELECT * FROM microservices.product;
SELECT * FROM microservices.orders;
SELECT * FROM microservices.order_line_items;
SELECT * FROM microservices.orders_order_line_items;
```

## ENDPOINTS INFO:
### PRODUCT: 
#### POST: localhost:8080/api/product
RAW:JSON=
```
{
    "name": "Samsung S20 Ultra",
    "description": "A mobile computer in your Pocket",
    "price": 1200 
}
```
#### GET: localhost:8080/api/product

### ORDER
#### POST: localhost:8081/api/order
RAW: JSON : ARRAY
```
{
"orderLineItemsDtosList":
[{
    "skuCode":"samsung_s20_ultra",
    "price":1200,
    "quantity":1
}]}
```

#### GET: localhost:8081/api/order

### Security: Keycloak Settings (Optional)
- Install Docker and start it.
- Open Powershell or terminal and paste following command
- https://www.keycloak.org/getting-started/getting-started-docker
- Go to localhost:8181 and create realm
- Once Realm is created click on Clients and Select Add Client 
```
docker run -p 8181:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:19.0.2 start-dev
```

### Resilience4j
#### What is Resilience4j? 
- Resilience4j is a lightweight, easy-to-use fault tolerance library inspired by Netflix Hystrix.
- Documentation: https://resilience4j.readme.io/docs

## WHAT'S NEW !!!
### DESIGN
UPDATE:2022-08-30
- Application coding initiated
### Eureka Service
UPDATE:2022-08-31
- Eureka Service has been added for Service discovery.
- Multiple Instance support has been added for Inventory Service.
- Cross application call initiated from Order to Inventory service via Spring WebFlux. 
### Resilience4j
UPDATE:2022-09-20
- Resilience4j in progress. 

## WHAT'S COMING
- KAFKA based cross application event driven synchronous communication.
- Spring Security based OAuth login
- API Gateway for enhance request delegation
- Docker based application and multi-module support
- Clusterization with Kubernetes.
- Basic HTML CSS based FrontEnd
- Enhanced application Security
- Circuit Breaker/ Resilience4J
- Distributed Tracing



