package com.github.frtu.sample.kafka.sink.async

import java.util.concurrent.TimeUnit
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service

@Service
class ProducerEmailSource {
    @Value("\${application.channel.email-source.topic}")
    lateinit var outputSource: String

    @Autowired
    lateinit var kafkaTemplate: KafkaTemplate<String, ByteArray>

    fun send(message: String): SendResult<String, ByteArray> {
        logger.info("Sending to topic:$outputSource message:$message")

        val record = ProducerRecord<String, String>(outputSource, message)
        val result = kafkaTemplate.send(outputSource, message.toByteArray()).get(1L, TimeUnit.SECONDS)
        val metadata: RecordMetadata = result.recordMetadata

        logger.info(
            "Sent record(key={} value={}) meta(topic={}, partition={}, offset={})",
            record.key(), record.value(), metadata.topic(), metadata.partition(), metadata.offset()
        )
        return result
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}