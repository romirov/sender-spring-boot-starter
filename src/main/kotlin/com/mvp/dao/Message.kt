package com.mvp.dao

abstract class Message(
    open val author: String,
    open val text: String
)

data class KafkaMessage(
    override val author: String,
    override val text: String
): Message(author = author, text = text)
