package com.github.frtu.mail

//import org.junit.Test
//import org.junit.runner.RunWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.cloud.stream.messaging.Processor
//import org.springframework.cloud.stream.test.binder.MessageCollector
//import org.springframework.integration.support.MessageBuilder
//import org.springframework.test.annotation.DirtiesContext
//import org.springframework.test.context.ContextConfiguration
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
//import kotlin.test.assertEquals
//
//
//@RunWith(SpringJUnit4ClassRunner::class)
//@ContextConfiguration(classes = [Application::class])
//@DirtiesContext
//class KafkaApplicationTests {
//    @Autowired
//    private val pipe: Processor? = null
//
//    @Autowired
//    private val messageCollector: MessageCollector? = null
//
//    @Test
//    fun whenSendMessage_thenResponseShouldUpdateText() {
//        val inputMessage = "This is my message"
//        pipe?.input()
//            ?.send(
//                MessageBuilder.withPayload(inputMessage)
//                    .build()
//            )
//        val payload = messageCollector!!.forChannel(pipe?.output())
//            .poll()
//            .payload
//        assertEquals("processed:${inputMessage}", payload.toString())
//    }
//}