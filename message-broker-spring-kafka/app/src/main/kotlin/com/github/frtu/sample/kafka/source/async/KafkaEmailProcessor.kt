package com.github.frtu.sample.kafka.source.async

import com.github.frtu.logs.core.RpcLogger
import com.github.frtu.logs.core.RpcLogger.entry
import com.github.frtu.logs.core.StructuredLogger.message
import com.github.frtu.sample.kafka.persistence.basic.IEmailRepository
import com.github.frtu.sample.kafka.source.async.KafkaConfiguration.Companion.DEFAULT_LISTENER_CONTAINER_FACTORY
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.DltHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.kafka.retrytopic.DltStrategy
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy
import org.springframework.kafka.support.Acknowledgment
import org.springframework.retry.annotation.Backoff
import org.springframework.stereotype.Service


@Service
class KafkaEmailProcessor(
    private val trigger: Trigger,
    private val dltTrigger: DltTrigger,
) {
//    @KafkaHandler

    @KafkaListener(
        topics = ["\${application.channel.email-source.topic}"],
        groupId = "consumer-group-email-2",
        concurrency = "2",
        containerFactory = DEFAULT_LISTENER_CONTAINER_FACTORY,
        properties = [
            "max.poll.records=\${application.channel.email-source.max-poll.records}",
            "max.poll.interval.ms=\${application.channel.email-source.max-poll.interval-ms}",
        ]
    )
    @RetryableTopic(
        kafkaTemplate = "kafkaTemplate",
        attempts = "2",
        backoff = Backoff(
            delay = 100,
            maxDelay = 5000,
            multiplier = 1.5,
        ),
        timeout = "\${application.channel.email-source.retry.timeout}",
        autoCreateTopics = "true",
        topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
        dltStrategy = DltStrategy.ALWAYS_RETRY_ON_ERROR,
        autoStartDltHandler = "\${application.channel.email-source.drain-dlt:true}",
        include = [RuntimeException::class],
    ) // https://docs.spring.io/spring-kafka/reference/retrytopic/retry-config.html
    fun listen(
        record: ConsumerRecord<String, ByteArray>,
        acknowledgment: Acknowledgment,
    ) {
        rpcLogger.info(
            message("Event received"),
            entry("topic", record.topic()),
            entry("key", record.key()),
            entry("partition", record.partition()),
            entry("offset", record.offset()),
            entry("timestamp", record.timestamp())
        )
        try {
            trigger.received(TriggerData(record.value().decodeToString(), record.topic()))
            acknowledgment.acknowledge()
        } catch (th: Throwable) {
            logger.error("Found exception. Message=${th.message}", th)
            throw th
        }
    }

    @DltHandler
    fun dlt(
        record: ConsumerRecord<String, ByteArray>,
    ) {
        rpcLogger.info(
            message("DLT Event received"),
            entry("topic", record.topic()),
            entry("key", record.key()),
            entry("partition", record.partition()),
            entry("offset", record.offset()),
            entry("timestamp", record.timestamp())
        )
        dltTrigger.received(TriggerData(record.value().decodeToString(), record.topic()))
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
    internal val rpcLogger = RpcLogger.create(logger)
}
