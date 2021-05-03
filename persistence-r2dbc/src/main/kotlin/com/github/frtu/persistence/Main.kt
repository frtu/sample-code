package com.github.frtu.persistence

import com.github.frtu.persistence.r2dbc.R2dbcConfiguration
import org.springframework.context.annotation.*

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@Import(R2dbcConfiguration::class)
class AppConfig

fun main() {
    val context = AnnotationConfigApplicationContext(AppConfig::class.java)
    println(context)
}