package com.github.frtu.persistence

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import com.github.frtu.persistence.r2dbc.Email
import com.github.frtu.persistence.r2dbc.R2dbcConfiguration
import com.github.frtu.persistence.r2dbc.STATUS
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

@Configuration
@Import(R2dbcConfiguration::class)
class RepositoryPopulatorConfig {
    @Bean
    fun initDatabase(repository: CoroutineCrudRepository<Email, UUID>): CommandLineRunner {
        val receiverList = listOf("rndfred@gmail.com", "rndfred@hotmail.com")
        val statusList = STATUS.values().toList()

        return CommandLineRunner { args: Array<String?>? ->
            runBlocking {
                val totalPopulation = 100
                for (i in 1..totalPopulation) {
                    var receiverEmail = pickOneBasedOnSeed(receiverList, i)
                    var status = pickOneBasedOnSeed(statusList, i)

                    repository.save(
                        Email(
                            receiverEmail, "Mail subject",
                            "Lorem ipsum dolor sit amet.", status.name
                        )
                    )

                    // Progress bar
                    if ((i % 10) == 0) {
                        print(".")
                    }
                }

                repository.findAll().toList(mutableListOf())
                    .forEach { email -> println(email) }
            }
        }
    }

    private fun <T> pickOneBasedOnSeed(list: List<T>, seed: Int): T {
        return list.get(seed % list.size)
    }
}

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
