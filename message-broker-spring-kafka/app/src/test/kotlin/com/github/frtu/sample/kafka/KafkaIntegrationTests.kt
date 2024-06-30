package com.github.frtu.sample.kafka

import com.github.frtu.sample.kafka.sink.async.ProducerEmailSource
import com.github.frtu.sample.kafka.source.async.DltTrigger
import com.github.frtu.sample.kafka.source.async.Trigger
import com.github.frtu.sample.kafka.source.async.TriggerData
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import java.util.UUID
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
    @Value("\${application.channel.email-source.topic}") private val topic: String,
    @Autowired private val trigger: Trigger,
    @Autowired private val dltTrigger: DltTrigger,
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
        //--------------------------------------
        // 1. Init
        //--------------------------------------
        val totalMessages = 2
        val latch = CountDownLatch(totalMessages + 2)

        //--------------------------------------
        // 1b. Prepare MOCK
        //--------------------------------------
        val slot = slot<TriggerData>()
        every { trigger.received(capture(slot)) } answers {
            latch.countDown()
            if (latch.count <= 2L) {
                throw RuntimeException("final event")
            }
        }

        val dltSlot = slot<TriggerData>()
        every { dltTrigger.received(capture(dltSlot)) } answers {
            latch.countDown()
        }

        //--------------------------------------
        // 2. Execute
        //--------------------------------------
        val itemIds: MutableSet<UUID> = HashSet()
        for (i in 0 until totalMessages) {
            val itemId = UUID.randomUUID()
            kafkaClient.send(itemId.toString())
            itemIds.add(itemId)
        }

        latch.await(10, TimeUnit.SECONDS)

        //--------------------------------------
        // 3. Validate
        //--------------------------------------
        slot.isCaptured shouldBe true
        slot.captured.topic shouldBe "$topic-retry"

        dltSlot.isCaptured shouldBe true
        dltSlot.captured.topic shouldBe "$topic-dlt"
    }
}

@Configuration
class TestConfig {
    @Bean
    @Primary
    fun trigger() = mockk<Trigger>(relaxed = true)

    @Bean
    @Primary
    fun dlttrigger() = mockk<DltTrigger>(relaxed = true)
}