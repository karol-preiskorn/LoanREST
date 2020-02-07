# Task

Simple loan issuing application.

## Business requirements

- Client can apply for loan asking for amount and term.
- Loan application evaluation: Application is risky if:
    - Loan application is done for max amount and loan application is created between 0:00–6:00
    - Client Applied 3 times using the same IP address.
- Loan is granted if loan application goes thru risk evaluation without errors.
- Once within loan term client can apply for term to be prolonged up to 14 days.
 
## Technical requirements

- `SOLID`
- Backend only, no GUI
- `REST API`
- Unit tests
- Acceptance test for positive example

# Analyse 

## Loan

static MAX_LOAN = 1000

- Id        [Int artifical id]
- LoanName  [String]
- Amount    [Long]   
- TermAt    [Date]
- CreatedAt [Date]
- UpdatedAT [Date]
- ApplyIP   [String]

## riskEvaluation

- amount > MAX_LOAN
- applyDate <0:00 - 6:00>
- applyIP same for 3 last apply

## REST

### put - apply for loan

URL: \clientName\amount\term
return: oki/declayned

### get - list of loan

\clinetName

### prolog loan

\clientName\amount\term\prolong

- once

## UnitTest 5

- take Loan < MAX_LOAN out of <00:00 - 06:00> - accepted
- take loan > MAX_LOAN - declined
- take loan from different IP
- take loan in different time <00:00 - 06:00>
- prolong loan - chceck chenges
- prolong two times loan - declined

# Impementation

- http://start.spring.io/
- archetype `spring-web-app`
- -`-add-opens` and dependency for  jaxb-api for suppres warning Illegal reflective access by org.springframework.cglib.core.ReflectUtils$1 --> [StackOverflow](https://stackoverflow.com/questions/46671472/illegal-reflective-access-by-org-springframework-cglib-core-reflectutils1)
- sample data in `import.sql`

## Explore Rest APIs

The app defines following CRUD APIs.

- `GET` http://localhost:8080 - info about API
- `GET` http://localhost/api/loans - get all lonas
- `POST` http://localhost/api/loans - create new loan
```json
{
    "prolongedTerm": false,
    "loanName": "IBM",
    "ip": "127.0.0.1",
    "loanId": 1,
    "createdAt": "2020-02-06T22:19:44.000+0000",
    "termAt": "2020-03-06T22:19:44.000+0000"
}
```
- `GET` http://localhost/api/loans/{loanId} - get id's loan
```json
http://localhost:8080/api/loans/1

{
    "prolongedTerm": false,
    "loanName": "Cabacki",
    "ip": "127.0.0.1",
    "loanId": 1,
    "createdAt": "2020-02-06T22:19:44.000+0000",
    "termAt": "2020-03-06T22:19:44.000+0000"
}
```
- `PUT` http://localhost/api/loans/{loanId} - create
- `PUT` http://localhost/api/loans/prolong/{loanId} - prolong loan
- `DELETE` http://localhost/api/loans/{loanId}

You can test them using postman or any other rest client.

Check: http://localhost/loans

## HSQL at mem
`spring.datasource.url: jdbc:hsqldb:mem:restdb`

## Database Initialization

In a JPA-based applications, we can either choose to let Hibernate create the schema using entity classes or use `schema.sql`, but we cannot do both.

Make sure to disable `spring.jpa.hibernate.ddl-auto` if using `schema.sql` in `application.properties`

Schema will be created using schema.sql and data.sql files

application.properties

```properties
spring.jpa.hibernate.ddl-auto=none
```

schama.sql

```iso92-sql
DROP TABLE IF EXISTS TBL_EMPLOYEES;
  
CREATE TABLE TBL_EMPLOYEES (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    email VARCHAR(250) DEFAULT NULL
);
```
