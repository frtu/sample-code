package com.github.frtu.workflow

import com.github.frtu.workflow.domain.EmployeeService
import org.activiti.engine.IdentityService
import org.activiti.engine.identity.Group
import org.activiti.engine.identity.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableAutoConfiguration
class Application {
    @Bean
    fun initializer(employeeService: EmployeeService): CommandLineRunner = CommandLineRunner { args: Array<String?> ->
        for (arg in args) {
            logger.info(arg)
        }
//        employeeService.createEmployee()
    }

    @Bean
    fun usersAndGroupsInitializer(identityService: IdentityService): InitializingBean {
        return InitializingBean {
            val group: Group = identityService.newGroup("user")
            group.name = "users"
            group.type = "security-role"
            identityService.saveGroup(group)
            val admin: User = identityService.newUser("admin")
            admin.password = "admin"
            identityService.saveUser(admin)
        }
    }

    internal val logger = LoggerFactory.getLogger(this::class.java)
}

fun main(args: Array<String>) {
    try {
        SpringApplication.run(Application::class.java, *args)
    } catch (th: Throwable) {
        th.printStackTrace()
    }
}