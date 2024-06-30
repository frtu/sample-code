# Project - message-broker-spring-kafka

## About

Sample spring kafka application demonstrating :

* Kafka publishing using JMH
* Kafka consuming using `KafkaListener` & `RetryableTopic` from spring-kafka

## Environment

### JMH

* Starting JMH with spring boot & `BenchmarkBase` class : https://www.lydtechconsulting.com/blog-jmh.html
* IntelliJ plugin : https://github.com/artyushov/idea-jmh-plugin

### Kafka docker-compose

You can get a local kafka using [kpow-local](https://github.com/factorhouse/kpow-local)

![kpow-message-publish.png](_docs_%2Fimage%2Fkpow-message-publish.png)

## Troubleshooting

### JMH

#### META-INF/BenchmarkList not found

If you get `META-INF/BenchmarkList` cannot be found, you may need to add `jmh-generator-annprocess` to your project &
force this file generation using :

```
mvn clean process-sources
```

#### META-INF/BenchmarkList is empty

If File is empty it's because you need to put @Benchmark class into `main` instead of `test`.
