package com.github.frtu.mail.producer

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaTemplate

/**
 * Based on :
 * @see <a href="https://docs.spring.io/spring-kafka/reference/html/#introduction">Spring Kafka</a>
 * @author Frédéric TU
 */
@SpringBootApplication
class ProducerEmailSourceApplication {
    @Value("\${application.topic.email-source}")
    lateinit var outputSource: String

    @Bean
    fun topicEmailSource(): NewTopic? {
        return TopicBuilder.name(outputSource)
            .partitions(10)
            .replicas(1)
            .build()
    }

    @Bean
    fun runner(kafkaTemplate: KafkaTemplate<String?, String?>): ApplicationRunner? {
        return ApplicationRunner { args: ApplicationArguments? ->
            kafkaTemplate.send(outputSource, "email_message")
        }
    }
}

fun main(args: Array<String>) {
    System.getProperties().put("server.port", 8083);
    runApplication<ProducerEmailSourceApplication>(*args)
}