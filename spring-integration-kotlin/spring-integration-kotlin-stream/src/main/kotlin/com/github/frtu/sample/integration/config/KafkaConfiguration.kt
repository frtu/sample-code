package com.github.frtu.sample.integration.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.kafka.dsl.Kafka
import org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter.ListenerMode
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer

@Configuration
@ComponentScan("com.github.frtu.sample.integration.service")
class KafkaConfiguration {
    @Value("\${spring.kafka.bootstrap-servers}")
    private val bootstrapServers: String? = null

    @Value("\${application.topic.integration-source}")
    private val inputTopic: String? = null

    @Bean
    @Throws(Exception::class)
    fun kafkaReader(): IntegrationFlow =
        IntegrationFlows
            .from(
                Kafka.messageDrivenChannelAdapter(
                    KafkaMessageListenerContainer(
                        consumerFactory(),
                        ContainerProperties(inputTopic)
                    ), ListenerMode.record
                )
            )
            .channel(KAFKA_INPUT)
            .get()

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = "kafkaListener"
        return DefaultKafkaConsumerFactory(props)
    }

    companion object {
        const val KAFKA_INPUT = "kafka-input"
    }
}