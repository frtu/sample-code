package com.github.frtu.ai.os.tool

data class FunctionParameter(
    val name: String,
    val description: String? = null,
    val type: String,
    val schema: String, // Need more structure
)