package com.mvp.kafka.configuration.prop

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.kafka")
class KafkaConsumerProperties @ConstructorBinding constructor(
    val properties: KafkaProperties.Consumer
)