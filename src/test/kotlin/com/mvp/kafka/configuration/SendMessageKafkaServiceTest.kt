package com.mvp.kafka.configuration

import com.mvp.AbstractSpringBootTest
import com.mvp.common.dao.IMessage
import com.mvp.common.dao.KafkaMessageImpl
import com.mvp.kafka.service.SendMessageKafkaService
import io.kotest.matchers.shouldNotBe
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext

@EmbeddedKafka(topics = ["#{senderProperties.topic}"], partitions = 1, bootstrapServersProperty = "#{senderProperties.bootstrapServers}")
@DirtiesContext
class SendMessageKafkaServiceTest(
    private val producerService: SendMessageKafkaService,
    private val kafkaRepo: MutableList<IMessage>
) : AbstractSpringBootTest(){

    @Test
    fun testSendAndReceive() {
        val message = KafkaMessageImpl(listOf(), "Test", "Test")
        producerService.sendMessage(message)
        Thread.sleep(10_000)

        kafkaRepo.size shouldNotBe 1
    }
}