package com.github.frtu.sample.kafka.benchmark.producer

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder

/**
 * Based on :
 * @see <a href="https://docs.spring.io/spring-kafka/reference/html/#introduction">Spring Kafka</a>
 * @author Frédéric TU
 */
@SpringBootApplication
class ProducerEmailSourceApplication {
    @Autowired
    lateinit var producerEmailSource: ProducerEmailSource

    @Bean
    fun topicEmailSource(): NewTopic? {
        return TopicBuilder.name(producerEmailSource.outputSource)
            .partitions(1)
            .replicas(1)
            .build()
    }

    @Bean
    fun runner(): ApplicationRunner? = ApplicationRunner { args: ApplicationArguments? ->
        producerEmailSource.send("startup_email_message")
    }
}

fun main(args: Array<String>) {
    System.getProperties().put("server.port", 8090);
    runApplication<ProducerEmailSourceApplication>(*args)
}
