import com.github.frtu.sample.kafka.benchmark.KafkaBenchmarkTest
import org.openjdk.jmh.profile.GCProfiler
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder

object BenchmarkRunner

fun main() {
    val opt = OptionsBuilder()
        .include(KafkaBenchmarkTest::class.java.getSimpleName())
        //            .warmupIterations(10)
        //            .warmupTime(TimeValue.seconds(1))
        .measurementIterations(1)
        //            .measurementTime(TimeValue.seconds(10))
        .forks(200) //0 makes debugging possible
        .shouldFailOnError(true)
        .addProfiler(GCProfiler::class.java)
        .build()
    Runner(opt).run()
}
