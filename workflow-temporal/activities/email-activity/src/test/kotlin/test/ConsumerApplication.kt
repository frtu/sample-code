package test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Based on :
 * @see <a href="https://docs.spring.io/spring-kafka/reference/html/#introduction">Spring Kafka</a>
 * @author Frédéric TU
 */
@SpringBootApplication
class ConsumerApplication

fun main(args: Array<String>) {
    System.getProperties()["server.port"] = 8084
    runApplication<ConsumerApplication>(*args)
}