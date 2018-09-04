#Tender

This application was created with SPRING INITIALIZR. You can find documentation and help at [https://start.spring.io/](https://start.spring.io/).
Purpose of the application is to create a simple REST API with Spring boot.

## How to Run
This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the java -jar command.

* Build: `mvn install`
* Run: `java -jar target/tender-0.0.1-SNAPSHOT.jar` or start TenderApplication class from your favourite IDE
* Install lombok plugin into your favourite IDE.

## About the Service
The service is just a simple tender review REST service. It uses an in-memory database (H2) to store the data. You can also do with a relational database like MySQL or PostgreSQL. If your database connection properties work, you can call REST endpoints, and the list should be visible in swagger documentation on url [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

### List of REST endpoints

#### Offer Resource

A. Create new Offer
```
POST /offers
Accept: application/json
Content-Type: application/json

{
    "bidderId": 0,
    "description": "string",
    "id": 0,
    "status": "PENDING",
    "tenderId": 0
}

RESPONSE: HTTP 200
Location header: http://localhost:8080/offers       
```

B. List of offers have been returned for selected bidder-tender combination
```
GET /offers
Content-Type: application/json
Parameters:
bidderId: long
tenderId: long

[
  {
    "bidderId": 0,
    "description": "string",
    "id": 0,
    "status": "PENDING",
    "tenderId": 0
  }
]

RESPONSE: HTTP 200
Location header: http://localhost:8080/offers?bidderId=0&tenderId=0
```

C. Marks offer as accepted and closes related tender.
```
POST /offers/{offerId}/accept
Accept: application/json
Content-Type: application/json

{
    "bidderId": 0,
    "description": "string",
    "id": 0,
    "status": "ACCEPTED",
    "tenderId": 0
}

RESPONSE: HTTP 200
Location header: http://localhost:8080/offers/0/accept     
```

#### Tender Resource

A. Create new Tender
```
POST /tenders
Accept: application/json
Content-Type: application/json

{
  "description": "string",
  "id": 0,
  "issuerId": 0,
  "status": "OPEN"
}

RESPONSE: HTTP 200
Location header: http://localhost:8080/tenders       
```

B. Returns all tenders in the system
```
POST /tenders
Accept: application/json
Content-Type: application/json

[
  {
    "description": "string",
    "id": 0,
    "issuerId": 0,
    "status": "OPEN"
  }
]

RESPONSE: HTTP 200
Location header: http://localhost:8080/tenders       
```

C. Returns filtered list of tenders based on issuer id
```
POST /tenders/filter
Accept: application/json
Content-Type: application/json
Parameters:
issuerId: long

[
  {
    "description": "string",
    "id": 0,
    "issuerId": 0,
    "status": "OPEN"
  }
]

RESPONSE: HTTP 200
Location header: http://localhost:8080/tenders/filter?issuerId=0     
```

## Technologies

### Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (among other types of applications). It provides many of the usual Spring facilities that can be configured easily usually without any XML. It provides embedded tomcat, so there is no need to install addition server container.
For persistence layer, we've chosen Spring Data in combination with Hibernate.

We've used Spring for clear separation of modules:

* resource: REST api 
* service: business logic
* Persistence: storing data

### Database
For this example application, I'm using the [H2 Database Engine](http://www.h2database.com/html/main.html), since no server installation is needed.
After the application is started, H2 console can be accessed on /h2-console with user 'sa' and password 'sa'.

For testing purposes, H2 in-memory database is used. I've decided to implement integration testing on service layer. Test classes are annotated as Transactional, so transactions will rollback after each test method execution.

### Database Migration
For the database migration, [Liquidbase Project](https://www.liquibase.org/) has been chosen cause it's database-independent. It can easily be changed latter to support any other SQL database server, without changing the line of code.
On first server startup, example entities are created for Bidder, Issuer and tender. List of these entities can be found in corresponding CSV files in resources folder. Independent set of entities is used for test and dev databases.

### Project Lombok
Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java. It helps us to avoid typing a lot og boilerplate code, like constructors, getters and setters...