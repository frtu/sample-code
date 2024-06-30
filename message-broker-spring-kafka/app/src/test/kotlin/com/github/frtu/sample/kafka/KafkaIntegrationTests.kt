package com.github.frtu.sample.kafka

import com.github.frtu.sample.kafka.sink.async.ProducerEmailSource
import com.github.frtu.sample.kafka.source.async.Trigger
import com.github.frtu.sample.kafka.source.async.TriggerData
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.test.EmbeddedKafkaBroker
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@EnableKafka
@SpringBootTest(
    classes = [
        TestConfig::class,
        Application::class,
    ], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@EmbeddedKafka(controlledShutdown = true, topics = ["input-email"])
@TestPropertySource(
    properties = [
        "spring.kafka.bootstrap-servers=\${spring.embedded.kafka.brokers}"
    ]
)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class KafkaIntegrationTest(
    @Autowired private val trigger: Trigger,
    @Autowired private val kafkaClient: ProducerEmailSource,
    @Autowired private val embeddedKafkaBroker: EmbeddedKafkaBroker,
    @Autowired private val registry: KafkaListenerEndpointRegistry,
) {
    @BeforeAll
    fun setup() {
        // Wait until the partitions are assigned.
//        registry.listenerContainers.stream().forEach { container: MessageListenerContainer ->
//            ContainerTestUtils.waitForAssignment(container, embeddedKafkaBroker.partitionsPerTopic)
//        }
    }

    /**
     * Test the standard scenario where items are created first before update events are received to update them.
     *
     * Each item is created with NEW status, and the update event transitions it to ACTIVE status.
     */
    @Test
    fun testCreateAndUpdateItems() {
        val totalMessages = 2

        val slot = slot<TriggerData>()
        val latch = CountDownLatch(totalMessages + 2)
        every { trigger.received(capture(slot)) } answers {
            latch.countDown()
            if (latch.count == 0L) {
                throw RuntimeException("final event")
            }
        }

        val itemIds: MutableSet<UUID> = HashSet()

        // Create new items.
        for (i in 0 until totalMessages) {
            val itemId = UUID.randomUUID()
            kafkaClient.send(itemId.toString())
            itemIds.add(itemId)
        }

        latch.await(10, TimeUnit.SECONDS)

        slot.isCaptured shouldBe true
        println(slot.captured)
    }
}

@Configuration
class TestConfig {
    @Bean
    @Primary
    fun trigger() = mockk<Trigger>(relaxed = true)
}