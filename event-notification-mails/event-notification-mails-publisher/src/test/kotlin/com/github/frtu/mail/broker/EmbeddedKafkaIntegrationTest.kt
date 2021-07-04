package com.github.frtu.mail.broker

import com.github.frtu.mail.broker.consumer.ConsumerEmailSource
import com.github.frtu.mail.broker.producer.ProducerEmailSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import java.util.concurrent.TimeUnit

@SpringBootApplication
class ApplicationTest

/**
 * Based on :
 * @see <a href="https://www.baeldung.com/spring-boot-kafka-testing">Testing with Spring Kafka</a>
 */
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
class EmbeddedKafkaIntegrationTest {
    @Autowired
    lateinit var consumer: ConsumerEmailSource

    @Autowired
    lateinit var producer: ProducerEmailSource

    @Test
    @Throws(Exception::class)
    fun givenEmbeddedKafkaBroker_whenSendingtoSimpleProducer_thenMessageReceived() {
        producer.send("Sending with own simple KafkaProducer")
        consumer.latch.await(10000, TimeUnit.MILLISECONDS)
        assertThat(consumer.latch.count).isEqualTo(1L)
        assertThat(consumer.payload).contains(producer.outputSource)
    }
}