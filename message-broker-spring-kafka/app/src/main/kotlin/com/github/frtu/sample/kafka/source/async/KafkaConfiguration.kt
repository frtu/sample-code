package com.github.frtu.sample.kafka.source.async

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaRetryTopic
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.scheduling.annotation.EnableScheduling


@Configuration
@EnableKafkaRetryTopic
@EnableScheduling
class KafkaConfiguration {
//    @Bean
//    fun greetingKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
//        val factory = ConcurrentKafkaListenerContainerFactory<String, Any>()
//        factory.containerProperties.ackMode = ContainerProperties.AckMode.RECORD
//        return factory
//    }
}