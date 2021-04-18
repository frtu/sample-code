# Project - event-notification-web-coroutine-flow

## About

UI :

* Email UI with Thymeleaf : [http://localhost:8080/](http://localhost:8080/)

REST API :

* Find all email : [http://localhost:8080/v1/emails](http://localhost:8080/v1/emails)
* Find all email with pagination : [http://localhost:8080/v1/emails?size=5&page=0](http://localhost:8080/v1/emails?size=5&page=0)
* Find email by ID : [http://localhost:8080/v1/emails/b726754d-df04-4a9f-8855-ccd04ed48a3c](http://localhost:8080/v1/emails/b726754d-df04-4a9f-8855-ccd04ed48a3c)
* Find everything just after email id : [http://localhost:8080/v1/emails/after/f74ae3c6-1c24-448b-9bb4-a45e617234cc](http://localhost:8080/v1/emails/after/f74ae3c6-1c24-448b-9bb4-a45e617234cc)

Search using query : [http://localhost:8080/v1/emails?status=SENT&size=1&page=0](http://localhost:8080/v1/emails?status=SENT&size=1&page=0)

* receiver : search by email recipient
* subject : search by email subject
* content : search by email content
* status : search by email status

## API

* Access Swagger API at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* Access API Docs (JSON) at [http://localhost:8080/v3/api-docs/](http://localhost:8080/v3/api-docs/)

## Release notes

### 0.0.1-SNAPSHOT - Current version

* Feature list