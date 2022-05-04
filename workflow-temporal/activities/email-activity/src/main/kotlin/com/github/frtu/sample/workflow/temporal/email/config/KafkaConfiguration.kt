package com.github.frtu.sample.workflow.temporal.email.config

import com.github.frtu.sample.workflow.temporal.email.activity.Email
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaConfiguration {
    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapServers: String

    @Value("\${application.topic.domain-source}")
    lateinit var outputSource: String

    @Bean
    fun topicSource(): NewTopic = TopicBuilder
        .name(outputSource)
        .partitions(10)
        .replicas(1)
        .build()

    fun producerFactory(): ProducerFactory<String, Email> = DefaultKafkaProducerFactory(
        mutableMapOf<String, Any?>(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java,
        )
    )

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Email> {
        return KafkaTemplate(producerFactory())
    }
}