package com.github.frtu.sample.ai.os.llm

import com.github.frtu.sample.ai.os.llm.model.Answer
import com.github.frtu.sample.ai.os.memory.Conversation

/**
 * Chat completion API taking a Conversation & returning an Answer
 */
interface Chat {
    suspend fun sendMessage(
        conversation: Conversation,
    ): Answer
}