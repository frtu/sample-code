package com.github.frtu.sample.kafka.benchmark

import com.github.frtu.sample.kafka.benchmark.producer.ProducerEmailSource
import com.github.frtu.sample.kafka.benchmark.producer.ProducerEmailSourceApplication
import java.util.concurrent.TimeUnit
import org.junit.runner.RunWith
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.TearDown
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.infra.Blackhole
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.junit4.SpringRunner


/**
 * View results captured in the project root here: jmh-query-results.txt
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(jvmArgsAppend = ["-Xms250m", "-Xmx500m"])
@RunWith(SpringRunner::class)
@SpringBootTest(classes = [ProducerEmailSourceApplication::class])
class KafkaBenchmarkTest : BenchmarkBase() {
    lateinit var context: ConfigurableApplicationContext

    @Setup
    fun setUp() {
        logger.debug("Setup benchmark & initialize context")
        context = SpringApplication.run(ProducerEmailSourceApplication::class.java)
    }

    @Benchmark
    fun pollEventsSelected(bh: Blackhole) {
        val producerEmailSource: ProducerEmailSource = context.getBean(ProducerEmailSource::class.java)
        val result = producerEmailSource.send("startup_email_message")
        bh.consume(result)
    }

    @TearDown(Level.Invocation)
    fun close() {
        context.close()
    }

    companion object {
        private val logger = LoggerFactory.getLogger(KafkaBenchmarkTest::class.java)
    }
}

