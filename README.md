

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



