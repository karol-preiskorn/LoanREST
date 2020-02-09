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
I assume that the risky loan request will be rejected.

## Loan entity

`static MAX_LOAN = 1000` - overrun 

- loanId `Int` artifical id.
- Amount `Long`
- LoanName  `String`
- Ip   `String`
- TermAt  `Date`
- historyLog `String` - info about allowed operations.
- CreatedAt `Date`
- UpdatedAT `Date`

example:

```json
{
    "id": 4,
    "amount": 999,
    "loanName": "IBM",
    "ip": "192.168.1.6",
    "termAt": "2020-02-22T16:01:08.756+0000",
    "historyLog": null,
    "createdAt": "2020-02-08T16:01:08.756+0000",
    "loanId": 4,
    "curentTime": "2020-02-08T16:01:08.900+0000",
    "prolongedTerm": false,
    "curentIp": "192.168.1.6"
}
```

## riskEvaluation

- `amount` > MAX_LOAN.
- `createdAt` <0:00 - 6:00>.
- `ip` same for 3 last apply in the same `loanName`.

## UnitTest 5

- Take Loan < MAX_LOAN out of <00:00 - 06:00> - accepted.
- Take loan > MAX_LOAN - declined.
- Take loan from different IP.
- Take loan in different time <00:00 - 06:00>.
- TrolongTerm - check changes.
- Prolong two times loan - declined.

# Impementation

- spring-boot-rest
- sample init data `HSQL` in [import.sql](src/main/resources/import.sql)
- junit test

## Setting environment (InteliJ)
- http://start.spring.io/ - [pom.xml](pom.xml)
- `-add-opens` and dependency for  jaxb-api for suppres warning Illegal reflective access by `org.springframework.cglib.core.ReflectUtils$1` [StackOverflow](https://stackoverflow.com/questions/46671472/illegal-reflective-access-by-org-springframework-cglib-core-reflectutils1)
- `GROOVY_TURN_OFF_JAVA_WARNINGS=true` temp fix `WARNING: Illegal reflective access by org.codehaus.groovy.vmplugin.v7.Java7$1`
- sample init data `HSQL` in [import.sql](src/main/resources/import.sql). 
```sqlite
insert into loans (amount, createdAt , Ip, LoanName, ProlongedTerm, TermAt, UpdatedAt) values (999, TIMESTAMP '2020-02-06 23:19:44', '127.0.0.1', 'Cabacki', false, TIMESTAMP '2020-03-06 23:19:44', null);
insert into loans (amount, createdAt , Ip, LoanName, ProlongedTerm, TermAt, UpdatedAt) values (300, TIMESTAMP '2020-02-06 23:19:44', '127.0.0.1', 'Abacki', false, TIMESTAMP '2020-03-06 23:19:44', null);
insert into loans (amount, createdAt , Ip, LoanName, ProlongedTerm, TermAt, UpdatedAt) values (60, TIMESTAMP '2020-02-06 23:19:44', '127.0.0.1', 'Babacki', false, TIMESTAMP '2020-03-06 23:19:44', null);
```

## REST APIs & logic

The app defines following simple CRU APIs. Without delete.

`MAX_LOAN` = 1000 define loan overrun defined in entity model as column.
```java
@Column
@NotNull(message = "Please enter correct amount of loan")
@Min(0)
@Max(MAX_LOAN)
private long Amount;
```

Important case for variable entity ie `nameLoan`, `cratedAt`.

- `GET` http://localhost:8080 - show *this* rendered `README.md` about `API`.
- `GET` http://localhost/api/loans - get all `lonas` list (sample init data in [import.sql](src/main/resources/import.sql))
- `POST` http://localhost/api/loans - create new `loan` example JSON:
```json5
{
    "prolongedTerm": false,
    "loanName": "IBM",
    "ip": "127.0.0.1",
    "loanId": 1,
    "createdAt": "2020-02-06T22:19:44.000+0000",
    "termAt": "2020-03-06T22:19:44.000+0000",
    "amount": 999
}
```
Resposne:
```json
{
    "id": 4,
    "amount": 999,
    "loanName": "IBM",
    "ip": "192.168.1.6",
    "termAt": "2020-02-22T16:01:08.756+0000",
    "historyLog": null,
    "createdAt": "2020-02-08T16:01:08.756+0000",
    "loanId": 4,
    "curentTime": "2020-02-08T16:01:08.900+0000",
    "prolongedTerm": false,
    "curentIp": "192.168.1.6"
}
```
- `GET` http://localhost/api/loans/{loanId} - get id's loan. Example [http://localhost:8080/api/loans/1](http://localhost:8080/api/loans/1)
```json
{
    "prolongedTerm": false,
    "loanName": "Cabacki",
    "ip": "127.0.0.1",
    "loanId": 1,
    "createdAt": "2020-02-06T22:19:44.000+0000",
    "termAt": "2020-03-06T22:19:44.000+0000"
}
```
- `PUT` http://localhost/api/loans/{loanId} - *not supported*
- `PUT` http://localhost/api/loans/prolong/{loanId} - prolong loan 14d
- `DELETE` http://localhost/api/loans/{loanId}

You can test them using postman (or any other rest client). Postman test collection:
https://www.getpostman.com/collections/f4f319431e96e245d5a8

## HSQL at mem
`spring.datasource.url: jdbc:hsqldb:mem:restdb`

### Database Initialization
In a JPA-based applications, we can either choose to let Hibernate create the schema using entity classes or use `schema.sql`, 
but we cannot do both. Make sure to disable `spring.jpa.hibernate.ddl-auto` if using `schema.sql` in `application.properties`

application.properties
```properties
spring.jpa.hibernate.ddl-auto=on
```

schema.sql

```sqlite
drop table if exists loans CASCADE
  
create table loans (
       Id bigint generated by default as identity (start with 1),
        Amount varbinary(255) not null,
        CreatedAt timestamp not null,
        Ip varchar(255),
        LoanName varchar(20),
        ProlongedTerm boolean,
        TermAt timestamp,
        UpdatedAt timestamp,
        primary key (Id)
    )
```
Sample init data `HSQL` in [import.sql](src/main/resources/import.sql).

# Help for Spring Boot REST

This module contains articles about Spring Boot RESTful APIs.

### Relevant Articles

- [HATEOAS for a Spring REST Service](https://www.baeldung.com/rest-api-discoverability-with-spring)
- [Versioning a REST API](https://www.baeldung.com/rest-versioning)
- [ETags for REST with Spring](https://www.baeldung.com/etags-for-rest-with-spring)
- [Testing REST with multiple MIME types](https://www.baeldung.com/testing-rest-api-with-multiple-media-types)
- [Testing Web APIs with Postman Collections](https://www.baeldung.com/postman-testing-collections)
- [Spring Boot Consuming and Producing JSON](https://www.baeldung.com/spring-boot-json)

### E-book

These articles are part of the Spring REST E-book:

1. [Bootstrap a Web Application with Spring 5](https://www.baeldung.com/bootstraping-a-web-application-with-spring-and-java-based-configuration)
2. [Build a REST API with Spring and Java Config](https://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-configuration)
3. [Http Message Converters with the Spring Framework](https://www.baeldung.com/spring-httpmessageconverter-rest)
4. [Spring’s RequestBody and ResponseBody Annotations](https://www.baeldung.com/spring-request-response-body)
5. [Entity To DTO Conversion for a Spring REST API](https://www.baeldung.com/entity-to-and-from-dto-for-a-java-spring-application)
6. [Error Handling for REST with Spring](https://www.baeldung.com/exception-handling-for-rest-with-spring)
7. [REST API Discoverability and HATEOAS](https://www.baeldung.com/restful-web-service-discoverability)
8. [An Intro to Spring HATEOAS](https://www.baeldung.com/spring-hateoas-tutorial)
9. [REST Pagination in Spring](https://www.baeldung.com/rest-api-pagination-in-spring)
10. [Test a REST API with Java](https://www.baeldung.com/integration-testing-a-rest-api)
