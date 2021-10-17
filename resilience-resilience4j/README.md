# Project - resilience-resilience4j

## About

Demonstrate Resilience4j using WebClient

* Access Blocking mode using [http://localhost:8080/blocking](http://localhost:8080/blocking)
* Access Non blocking mode using [http://localhost:8080/nonblocking](http://localhost:8080/nonblocking)

Retry :

* Access Blocking create mode using [http://localhost:8080/blocking/create](http://localhost:8080/blocking/create)

Monitoring :

* Check metrics definition : http://localhost:8080/actuator/metrics/resilience4j.retry.calls
* Metric values in [prometheus](http://localhost:8080/actuator/prometheus)

```
# HELP resilience4j_retry_calls_total The number of successful calls without a retry attempt
# TYPE resilience4j_retry_calls_total counter
resilience4j_retry_calls_total{kind="failed_with_retry",name="bridge",} 4.0
resilience4j_retry_calls_total{kind="successful_with_retry",name="bridge",} 6.0
resilience4j_retry_calls_total{kind="failed_without_retry",name="bridge",} 0.0
resilience4j_retry_calls_total{kind="successful_without_retry",name="bridge",} 4.0
```

## Release notes

### 0.0.1-SNAPSHOT - Current version

* Feature list