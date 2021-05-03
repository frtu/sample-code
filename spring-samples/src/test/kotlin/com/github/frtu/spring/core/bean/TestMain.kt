package com.github.frtu.spring.core.bean

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class TestConfig

fun main() {
    val context = AnnotationConfigApplicationContext(TestConfig::class.java)
    val dummyBean: DummyBean = context.getBean(DummyBean::class.java)
    println(dummyBean.key1)
}