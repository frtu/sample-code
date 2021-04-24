package org.springframework.data.r2dbc.core

class PreviousDatabaseClientBuilderFactory {
    companion object {
    }
    fun build(): DatabaseClient {
        return DefaultDatabaseClientBuilder().build()
    }
}