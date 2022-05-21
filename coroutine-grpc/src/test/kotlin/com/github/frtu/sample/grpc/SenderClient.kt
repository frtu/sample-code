package com.github.frtu.sample.grpc

import com.google.protobuf.Timestamp
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.io.Closeable
import java.time.Instant
import java.util.concurrent.TimeUnit

class SenderClient(private val channel: ManagedChannel) : Closeable {
    private val stub: SenderGrpcKt.SenderCoroutineStub = SenderGrpcKt.SenderCoroutineStub(channel)

    suspend fun send(name: String) {
        val time: Instant = Instant.now()
        val timestamp: Timestamp = Timestamp.newBuilder()
            .setSeconds(time.epochSecond)
            .setNanos(time.nano)
            .build()

        val request = email {
            this.name = name
            this.eventTime = timestamp
        }
        val response = stub.send(request)
        println("Received: ${response.message}")
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}

suspend fun main(args: Array<String>) {
    val port = 9090

    val channel = ManagedChannelBuilder.forAddress("localhost", port).usePlaintext().build()
    val client = SenderClient(channel)

    val user = args.singleOrNull() ?: "fred"
    client.send(user)
}
