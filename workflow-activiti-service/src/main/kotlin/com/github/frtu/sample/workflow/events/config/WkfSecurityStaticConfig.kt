package com.github.frtu.sample.workflow.events.config

import org.activiti.engine.IdentityService
import org.activiti.engine.identity.Group
import org.activiti.engine.identity.User
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WkfSecurityStaticConfig {
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
}