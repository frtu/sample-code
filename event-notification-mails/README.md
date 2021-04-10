# Project - event-notification-mails

## About

Microservice allowing to send email using SMTP. All emails sent are archived into database.

## Components

### Data storage layer

PostgreSQL 12, Docker & Flyway :

* **Database & DB User** : Started & autocreated with Docker & init script
* **Tables** : created & maintained with [Flyway](https://flywaydb.org/)
* **Scripts** : versionned and stored in application folder [db/migration](event-notification-mails-stream/src/main/resources/db/migration)

Start DB using scripts in project folder :

```
./script.bash
```

### Publisher

More info at [/event-notification-mails-publisher](/event-notification-mails-publisher)

### Subscriber / Streaming for project

More info at [/event-notification-mails-stream](/event-notification-mails-stream)

## Release notes

### 0.0.1-SNAPSHOT - Current version

* Feature list