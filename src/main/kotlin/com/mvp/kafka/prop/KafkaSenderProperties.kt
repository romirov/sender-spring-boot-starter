package com.mvp.kafka.prop

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.kafka")
data class KafkaSenderProperties @ConstructorBinding constructor(
    val enabled: Boolean,
    val bootstrapServers: String,
    val topic: String,
    val numPartitions: Int,
    val replicationFactor: Int,
    val producer: KafkaProperties.Producer
)