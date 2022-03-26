# Project - worklow-activiti

## About

Simple kotlin spring-boot project for activiti workflow 

## API

## Integration with Docker

Build your Docker image at ```mvn verify``` phase

Startup your application using :

```
docker run -d --name <instance-name> -p 8080:8080 -P <your-archetype-id>
```

* -d : startup as a daemon
* --name : give it a name

## Release notes

### 0.0.1-SNAPSHOT - Current version

* Feature list