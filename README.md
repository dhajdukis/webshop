# webshop template
Java RESTful Web Service for a webshop

## 1. Install&Run

### Used technologies

Java 11

Gradle 5.5

JUnit 5.5.2

Spring Boot 2.1.8

H2 database 1.4.199

### Run as a JAR file

Build:

`gradlew clean build`

Run the application:

`java -jar build/libs/webshop-1.0-SNAPSHOT.jar`

## 2. Get a list of products:

`curl -X GET http://localhost:8080/api/products`

Response body:
`[{"id":1,"name":"first","currentPrice":100.00,"lastUpdate":"2019-09-25T22:13:47.861347+02:00"},{"id":2,"name":"second","currentPrice":44.00,"lastUpdate":"2019-09-25T23:15:01.108711+02:00"}]`

## 3. Get one product:

`curl -X GET http://localhost:8080/api/products/1` 

Response Body:

`{"id":1,"name":"first","currentPrice":100.00,"lastUpdate":"2019-09-25T22:13:47.861347+02:00"}`

## 3. Update a product (Windows style):
`curl -X PUT http://localhost:8080/api/products/1 -H "Content-Type: application/json" --data "{\"id\":\"1\",\"name\":\"new_name\",\"currentPrice\" : \"777\"}"`

## 4. Create product (Windows style):
`curl -X POST http://localhost:8080/api/products -H "Content-Type: application/json" --data "{\"name\":\"third\",\"currentPrice\" : \"1253\"}"`

### Authors
Daniel Hajdu-Kis - dhajdukis
