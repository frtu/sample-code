# Project - worfklow-activiti-service

## About

Spring boot 1.x application using activiti as workflow engine.

## API

### Process API

* POST ```/processes``` : Create a new process with assignee as payload (ex : "Fred")
* GET ```/processes``` : List all running process
* GET ```/processes/{processInstanceId}/tasks``` : List all user tasks from a particular ```processInstanceId```
* POST ```/processes/{processInstanceId}/complete``` : Complete the existing process
* GET ```/processes/assignee/{assignee}``` : List all user tasks for a particular assignee

### Swagger with WebMVC

* Access Swagger API at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* Access API Docs (JSON) at [http://localhost:8080/v3/api-docs/](http://localhost:8080/v3/api-docs/)

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