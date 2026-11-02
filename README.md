# Spring Boot starter for Wodby

A production-oriented web application for the [Wodby Spring Boot service](https://github.com/wodby/service-spring-boot)
and [Spring Boot stack](https://github.com/wodby/stack-spring-boot).

It uses Spring Boot 4 on Java 21 or later and demonstrates:

- Spring MVC with a Thymeleaf page and a JSON endpoint
- Spring Data JPA on PostgreSQL, with the schema owned by Flyway migrations that run at startup
- Spring Boot Actuator health checks, which Wodby uses for readiness and the post-deployment check
- web-layer tests that run without a database, and Wodby CI

## Local development

Start PostgreSQL with a database, user and password named `app`, for example:

```shell
docker run --rm -p 5432:5432 -e POSTGRES_DB=app -e POSTGRES_USER=app -e POSTGRES_PASSWORD=app postgres:18
```

Then run the tests and the application:

```shell
./mvnw test
./mvnw spring-boot:run
```

Open <http://localhost:8080>. Useful endpoints are:

- `/` — a page that stores short notes in PostgreSQL
- `/api/status` — a JSON response with the Java and Spring Boot versions
- `/actuator/health` — the health endpoint, which includes the database

## Configuration on Wodby

`application.properties` holds local defaults only. On Wodby, the database link sets
`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` and `SPRING_DATASOURCE_PASSWORD`, which Spring Boot reads
by itself and which take precedence over the file. Environments of type `dev` activate the `dev` profile
(`application-dev.properties`), which shows error messages and health details.

Add schema changes as new files in `src/main/resources/db/migration` (`V2__...sql`, and so on); every deployment
applies them before the application starts serving. Hibernate only validates the schema.

The stack's Valkey and mail services are disabled by default. To use them, add
`spring-boot-starter-data-redis` or `spring-boot-starter-mail`, enable the service and link it: the links set
`SPRING_DATA_REDIS_*` and `SPRING_MAIL_*` for you.

## Development workspace

In a Wodby development workspace, the checkout is built with the Maven wrapper and the application restarts with
the new build whenever a file changes, usually within a few seconds. Build errors are in the service's logs.
