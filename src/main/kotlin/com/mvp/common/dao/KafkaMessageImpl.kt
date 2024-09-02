package com.mvp.common.dao

import org.apache.kafka.common.header.Header

data class KafkaMessageImpl(
    val headers: List<Header>,
    val author: String,
    val text: String
): IMessage