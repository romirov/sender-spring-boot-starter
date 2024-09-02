package com.mvp.kafka.serialize

import com.fasterxml.jackson.databind.ObjectMapper
import com.mvp.common.dao.IMessage

object KafkaSerializer {
    fun serialize(message: IMessage): String {
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(message)
    }

    fun deserialize(message: String): IMessage {
        val mapper = ObjectMapper()
        return mapper.readValue(message, IMessage::class.java)
    }
}