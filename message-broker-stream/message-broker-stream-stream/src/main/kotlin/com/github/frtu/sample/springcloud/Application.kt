package com.github.frtu.sample.springcloud

import com.github.frtu.logs.config.LogConfigAll
import com.github.frtu.sample.springcloud.loan.LoanProcessor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.cloud.stream.annotation.EnableBinding

@Import(LogConfigAll::class)
@SpringBootApplication
//@EnableR2dbcRepositories
@EnableBinding(LoanProcessor::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}