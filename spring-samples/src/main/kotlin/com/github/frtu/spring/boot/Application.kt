package com.github.frtu.spring.boot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@Import(SampleBootConfig::class)
@SpringBootApplication
class Application {
    @Autowired(required = false)
    var bean2: SampleBootConfig.Bean2? = null

    @Autowired(required = false)
    var bean3: SampleBootConfig.Bean3? = null

    @Autowired(required = false)
    var bean4: SampleBootConfig.Bean4? = null

    @Bean
    fun init(bean1: SampleBootConfig.Bean1): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            apply {
                // Always be here
                println(bean1)
                // doesn't have value => null
                println(bean2)
                // run if string is empty => null
                println(bean3)
                // Avoid if value is not here
                println(bean4)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
