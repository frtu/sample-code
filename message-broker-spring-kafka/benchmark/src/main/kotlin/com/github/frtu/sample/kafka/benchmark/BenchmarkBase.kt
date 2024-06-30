package com.github.frtu.sample.kafka.benchmark

import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.junit.Test
import org.openjdk.jmh.results.format.ResultFormatType
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.RunnerException
import org.openjdk.jmh.runner.options.OptionsBuilder
import org.openjdk.jmh.runner.options.TimeValue
import org.springframework.core.io.support.PropertiesLoaderUtils

abstract class BenchmarkBase {
    /**
     * Any benchmark, by extending this class, inherits this single @Test method for JUnit to run.
     */
    @Test
    @Throws(RunnerException::class, IOException::class)
    fun executeJmhRunner() {
        val properties = PropertiesLoaderUtils.loadAllProperties("benchmark.properties")
        val warmup = properties.getProperty("benchmark.warmup.iterations", "5").toInt()
        val iterations = properties.getProperty("benchmark.test.iterations", "5").toInt()
        val threads = properties.getProperty("benchmark.test.threads", "1").toInt()
        val resultFilePrefix = properties.getProperty("benchmark.global.resultfileprefix", "jmh-")
        val resultsFileOutputType = ResultFormatType.JSON
        val opt = OptionsBuilder()
            .include("\\." + this.javaClass.getSimpleName() + "\\.")
            .warmupIterations(warmup)
            .measurementIterations(iterations) // single shot for each iteration:
            .warmupTime(TimeValue.NONE)
            .measurementTime(TimeValue.NONE) // do not use forking or the benchmark methods will not see references stored within its class
            .forks(0)
            .threads(threads)
            .shouldDoGC(true)
            .shouldFailOnError(true)
            .resultFormat(resultsFileOutputType)
            .result(buildResultsFileName(resultFilePrefix, resultsFileOutputType))
            .shouldFailOnError(true)
            .jvmArgs("-server")
            .build()
        Runner(opt).run()
    }

    companion object {
        private fun buildResultsFileName(resultFilePrefix: String, resultType: ResultFormatType): String {
            val date = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("mm-dd-yyyy-hh-mm-ss")
            val suffix: String = when (resultType) {
                ResultFormatType.CSV -> ".csv"
                ResultFormatType.SCSV ->                 // Semi-colon separated values
                    ".scsv"

                ResultFormatType.LATEX -> ".tex"
                ResultFormatType.JSON -> ".json"
                else -> ".json"
            }
            return String.format("target/%s%s%s", resultFilePrefix, date.format(formatter), suffix)
        }
    }
}
