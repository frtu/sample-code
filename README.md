# Sample code

## About

Sample code for Kotlin. Can check :

Basic :

* [data-structure](data-structure) : basic data structure samples
* [coroutine-core](coroutine-core) : coroutine core samples
* [persistence-r2dbc](persistence-r2dbc) : for R2DBC persistence samples

Services :

* [spring-samples](spring-samples) : for Spring core & Spring Boot samples (properties, Unit Tests, ..)
* [microservices-kotlin](microservices-kotlin) : 2 microservices samples
* [event-notification-mails](event-notification-mails) : message publisher & consumer

## Guidelines

### IDE - Logger live template

For IntelliJ, create a Live Template at :

* Preferences... > Editor > Live Templates
* For Kotlin, Use + button on the right to create a new
* Abbreviation : log_decl
* Decription : Create a log declaration for the current class
* Template text : ```private val LOGGER: Logger = LoggerFactory.getLogger($enclosing_type$::class.java)```
* Edit Variables : enclosing_type -> kotlinClassName()
* Applicatable in Kotlin : class