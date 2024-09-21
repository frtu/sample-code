package com.github.frtu.sample.bot.slack.config

import com.github.frtu.sample.ai.os.llm.openai.OpenAiCompatibleChat
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("application.ai.os")
data class ChatApiProperties(
    val apiKey: String? = null,
    val model: String = OpenAiCompatibleChat.LOCAL_MODEL, // "mistral"
    val baseUrl: String = OpenAiCompatibleChat.LOCAL_URL, // "http://localhost:11434/v1/"
) {
    /**
     * https://platform.openai.com/docs/models/continuous-model-upgrades
     */
    fun isOpenAI() = model.startsWith("gpt-")
    fun validateOpenAIKey() = apiKey != null && apiKey.startsWith("sk-")
}
