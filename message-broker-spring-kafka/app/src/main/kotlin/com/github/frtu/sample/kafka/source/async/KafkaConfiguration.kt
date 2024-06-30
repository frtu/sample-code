package com.github.frtu.sample.kafka.source.async

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.annotation.EnableKafkaRetryTopic
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.scheduling.annotation.EnableScheduling


@Configuration
@EnableKafkaRetryTopic
@EnableScheduling
@Import(KafkaAutoConfiguration::class)
/**
 * @see <a href="https://docs.spring.io/spring-kafka/reference/retrytopic/retry-config.html">Retry config</a>
 */
class KafkaConfiguration
//    : RetryTopicConfigurationSupport()
{
    fun kafkaConsumerFactory(
        kafkaProperties: KafkaProperties,
    ): ConsumerFactory<String, ByteArray> {
        val consumerProperties = kafkaProperties.buildConsumerProperties(null)
        consumerProperties[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
        consumerProperties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"
        return DefaultKafkaConsumerFactory(consumerProperties)
    }

    @Bean(DEFAULT_LISTENER_CONTAINER_FACTORY)
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, ByteArray>,
    ): KafkaListenerContainerFactory<*> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ByteArray>()
        factory.consumerFactory = consumerFactory
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        factory.setAutoStartup(true)
        return factory
    }

    companion object {
        const val DEFAULT_LISTENER_CONTAINER_FACTORY = "defaultListenerFactory"
    }
}