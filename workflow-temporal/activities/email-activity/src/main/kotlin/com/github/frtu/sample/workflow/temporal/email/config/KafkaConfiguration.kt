package com.github.frtu.sample.workflow.temporal.email.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaConfiguration {
    @Value("\${application.topic.domain-source}")
    lateinit var outputSource: String

    @Bean
    fun topicSource(): NewTopic = TopicBuilder
        .name(outputSource)
        .partitions(10)
        .replicas(1)
        .build()
}