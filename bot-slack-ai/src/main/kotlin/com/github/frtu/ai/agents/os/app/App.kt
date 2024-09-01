package com.github.frtu.ai.agents.os.app

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.frtu.ai.os.llm.Chat
import com.github.frtu.ai.os.llm.openai.OpenAiCompatibleChat
import com.github.frtu.ai.os.memory.Conversation
import com.github.frtu.ai.os.tool.registry
import kotlinx.serialization.json.jsonPrimitive

suspend fun main() {
    val apiKey = "sk-xx"

    val functionRegistry = registry {
        function(
            name = "get_current_weather", description = "Get the current weather in a given location",
            kFunction2 = ::currentWeather, parameterClass = WeatherInfo::class.java, String::class.java,
        )
        function(
            name = "get_n_day_weather_forecast", description = "Get an N-day weather forecast",
            kFunction2 = ::currentWeather, parameterClass = WeatherInfoMultiple::class.java, String::class.java,
        )
    }

//    val chat = OpenAiChat(
//        apiKey = apiKey,
//        model = "gpt-3.5-turbo-0613",
//        functionRegistry = functionRegistry,
//        defaultEvaluator = { chatChoices -> chatChoices.first() }
//    )

    val chatOpenAI: Chat = OpenAiCompatibleChat(
        apiKey = apiKey,
//        functionRegistry = functionRegistry,
        defaultEvaluator = { chatChoices -> chatChoices.first() }
    )
    val chatOllama: Chat = OpenAiCompatibleChat(
        apiKey = "none",
        model = "mistral",
        baseUrl = "http://localhost:11434/v1/",
//        functionRegistry = functionRegistry,
        defaultEvaluator = { chatChoices -> chatChoices.first() }
    )
    val chat = chatOpenAI

    with(Conversation()) {
        system("Don't make assumptions about what values to plug into functions. Ask for clarification if a user request is ambiguous.")

        chat.sendMessage(user("What's the weather like in Glasgow, Scotland over the next x days?"))
        val response = chat.sendMessage(user("5 days"))
        println(response)

        val message = response.message
        message.functionCall?.let { functionCall ->
            this.addResponse(message)

            val functionToCall = functionRegistry.getFunction(functionCall.name).action

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
            println(secondResponse.message.content)
        } ?: println(message.content)
    }
}

fun currentWeather(location: String, unit: String): String {
    val weatherInfo = WeatherInfo(location, unit, "72", listOf("sunny", "windy"))
    return jacksonObjectMapper().writeValueAsString(weatherInfo)
}