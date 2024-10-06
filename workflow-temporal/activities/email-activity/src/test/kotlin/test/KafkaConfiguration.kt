package test

import com.github.frtu.sample.workflow.temporal.activity.email.Email
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConfiguration {
    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapServers: String

    @Value("\${application.topic.domain-source}")
    lateinit var inputSource: String

    @Bean
    fun topic(): NewTopic {
        return TopicBuilder.name(inputSource)
            .partitions(10)
            .replicas(1)
            .build()
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Email> = DefaultKafkaConsumerFactory(
        mutableMapOf<String, Any?>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.GROUP_ID_CONFIG to "kafkaListener",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest",
        ),
        StringDeserializer(),
        JsonDeserializer(Email::class.java),
    )
}