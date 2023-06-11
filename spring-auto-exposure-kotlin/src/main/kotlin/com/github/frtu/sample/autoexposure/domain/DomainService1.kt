package com.github.frtu.sample.autoexposure.domain

import com.github.frtu.sample.autoexposure.rpc.DomainService
import org.slf4j.LoggerFactory

@DomainService("service1")
class DomainService1 : DomainInterface {
    override fun exec(id: String) {
        logger.info("Service called id:$id")
    }

    private val logger = LoggerFactory.getLogger(this::class.java)
}