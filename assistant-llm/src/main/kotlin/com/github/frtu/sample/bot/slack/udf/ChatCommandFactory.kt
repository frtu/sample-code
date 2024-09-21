package com.github.frtu.sample.bot.slack.udf

import com.github.frtu.sample.ai.os.llm.Chat
import com.github.frtu.sample.ai.os.memory.Conversation
import com.github.frtu.sample.ai.os.tool.FunctionRegistry
import com.slack.api.bolt.handler.builtin.SlashCommandHandler
import kotlin.concurrent.thread
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChatCommandFactory {
    @Bean
    fun ask(
        // Chat engine
        chat: Chat,
        // For execution
        functionRegistry: FunctionRegistry? = null,
    ): SlashCommandHandler = SlashCommandHandler { req, ctx ->
        val commandArgText = req.payload.text
        val channelId = req.payload.channelId
        val channelName = req.payload.channelName

        val text = "You said $commandArgText at <#$channelId|$channelName>"
        ctx.logger.debug("Command /echo called : $text")

        // Immediate response to avoid timeout
        ctx.ack("Processing your request...")

        // Start asynchronous processing
        thread {
            try {
                // Simulate long-running task
                runBlocking {
//                    val job = launch {
                    with(Conversation()) {
                        system("Don't make assumptions about what values to plug into functions. Ask for clarification if a user request is ambiguous.")
                        val response = chat.sendMessage(user(commandArgText))
                        println(response)

                        val message = response.message
                        message.functionCall?.let { functionCall ->
                            this.addResponse(message)

                            val functionToCall = functionRegistry!!.getFunction(functionCall.name).action

                            val functionArgs = functionCall.argumentsAsJson()
                            val location = functionArgs.getValue("location").jsonPrimitive.content
                            val unit = functionArgs["unit"]?.jsonPrimitive?.content ?: "fahrenheit"
                            val numberOfDays = functionArgs.getValue("numberOfDays").jsonPrimitive.content

                            val secondResponse = chat.sendMessage(
                                function(
                                    functionName = functionCall.name,
                                    content = functionToCall(location, unit)
                                )
                            )
                            ctx.logger.debug("Finished: ${secondResponse.message.content}")
                            ctx.respond("Task completed! Result: ${secondResponse.message.content}")
                        } ?: run {
                            ctx.logger.debug("Finished: ${message.content}")
                            ctx.respond("Task completed! Result: ${message.content}")
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Send error message via response_url
                ctx.respond("Sorry, an error occurred while processing your request.")
            }
        }

        // Acknowledge the slash command
        ctx.ack()
    }
}