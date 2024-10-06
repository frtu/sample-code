package com.github.frtu.sample.workflow.temporal.email

import com.github.frtu.sample.workflow.temporal.activity.email.Email
import com.github.frtu.sample.workflow.temporal.activity.email.EmailSinkActivityImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import test.ConsumerSource
import java.util.*
import java.util.concurrent.TimeUnit

@SpringBootApplication
@EnableKafka
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
    lateinit var consumer: ConsumerSource

    @Autowired
    lateinit var producer: EmailSinkActivityImpl

    @Test
    @Throws(Exception::class)
    fun givenEmbeddedKafkaBroker_whenSendingtoSimpleProducer_thenMessageReceived() {
        val subscriptionId = UUID.randomUUID()
        producer.emit(
            Email(
                subject = "Confirmation of Subscription ID : $subscriptionId",
                content = "Sending with own simple KafkaProducer",
                id = subscriptionId,
            )
        )
        consumer.latch.await(10000, TimeUnit.MILLISECONDS)
        assertThat(consumer.latch.count).isEqualTo(0L)
        assertThat(consumer.payload).contains(producer.outputSource)
    }
}