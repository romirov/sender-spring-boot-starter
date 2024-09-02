package com.mvp.kafka.service

import com.mvp.common.dao.IMessage
import com.mvp.common.service.ISendMessageService
import com.mvp.kafka.prop.KafkaSenderProperties
import com.mvp.kafka.serialize.KafkaSerializer
import com.mvp.kafka.util.KafkaProducerId
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate

class SendMessageKafkaService(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val producerId: KafkaProducerId,
    private val properties: KafkaSenderProperties
): ISendMessageService {

    override fun sendMessage(message: IMessage) {
        kafkaTemplate.send(
            createProducerRecord(message)
        )
        kafkaTemplate.flush()
    }

    private fun createProducerRecord(message: IMessage): ProducerRecord<String, String> =
        ProducerRecord<String, String> (
        properties.topic,
        producerId.id,
        KafkaSerializer.serialize(message)
    )
}