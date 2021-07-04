package com.github.frtu.mail.consumer

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.TopicBuilder

/**
 * Based on :
 * @see <a href="https://docs.spring.io/spring-kafka/reference/html/#introduction">Spring Kafka</a>
 * @author Frédéric TU
 */
@SpringBootApplication
class ConsumerEmailSourceApplication {
    companion object {
        const val inputSource: String = "email-source"
    }
//    @Value("\${application.topic.email-source}")
//    lateinit var inputSource: String

    @Bean
    fun topic(): NewTopic {
        return TopicBuilder.name(inputSource)
            .partitions(8)
            .replicas(1)
            .build()
    }

    @KafkaListener(id = "consumer-1", topics = [inputSource])
    fun listen(consumerRecord: ConsumerRecord<Any, Any>) {
        listen(consumerRecord.toString())
    }

    fun listen(input: String) {
        logger.info("received payload='{}'", input);
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}

fun main(args: Array<String>) {
    runApplication<ConsumerEmailSourceApplication>(*args)
}