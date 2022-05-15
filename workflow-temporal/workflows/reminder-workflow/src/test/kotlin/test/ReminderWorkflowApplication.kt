package test

import com.github.frtu.sample.workflow.temporal.reminder.config.AllReminderConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(AllReminderConfig::class)
class ReminderWorkflowApplication

fun main(args: Array<String>) {
    runApplication<ReminderWorkflowApplication>(*args)
}