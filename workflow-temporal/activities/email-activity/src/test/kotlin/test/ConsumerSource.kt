package test

import com.github.frtu.sample.workflow.temporal.activity.email.Email
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.concurrent.CountDownLatch

@Service
class ConsumerSource {
    companion object {
        const val inputSource: String = "email-source"
    }

    val latch = CountDownLatch(1)
    var payload: String? = null

    @KafkaListener(id = "consumer-1", topics = [inputSource])
    fun listen(consumerRecord: ConsumerRecord<String, Email>) {
        listen(consumerRecord.toString())
    }

    fun listen(input: String) {
        logger.info("received payload='{}'", input);
        this.payload = input
        latch.countDown()
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}