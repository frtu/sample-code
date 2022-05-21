package com.github.frtu.sample.grpc.springboot

import com.github.frtu.sample.grpc.Email
import com.github.frtu.sample.grpc.SenderGrpcKt
import com.github.frtu.sample.grpc.sendResult
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.stereotype.Component

@Component
@GrpcService
class Sender : SenderGrpcKt.SenderCoroutineImplBase() {
    override suspend fun send(request: Email) = sendResult {
        message = "Email for '${request.name}' sent"
    }
}