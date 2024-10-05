package com.github.frtu.sample.workflow.temporal.subscription.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.kafka.dsl.Kafka
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter.ListenerMode
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer

@Configuration
@ComponentScan("com.github.frtu.sample.workflow.temporal.service")
class KafkaConfiguration {
    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapServers: String

    @Value("\${application.topic.domain-source}")
    lateinit var inputTopic: String

    @Bean
    @Throws(Exception::class)
    fun kafkaReader(): IntegrationFlow = IntegrationFlow
        .from(
            Kafka.messageDrivenChannelAdapter(
                KafkaMessageListenerContainer(consumerFactory(), ContainerProperties(inputTopic)),
                ListenerMode.record
            )
        )
        .channel(KAFKA_INPUT)
        .get()

    fun consumerFactory(): ConsumerFactory<String, String> = DefaultKafkaConsumerFactory(
        mutableMapOf<String, Any?>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.GROUP_ID_CONFIG to "kafkaListener"
        )
    )

    companion object {
        const val KAFKA_INPUT = "subscription-input"
    }
}