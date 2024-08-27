package com.mvp.kafka.configuration

import com.mvp.kafka.prop.KafkaSenderProperties
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfig(
    private val properties: KafkaSenderProperties
) {
    @Bean
    fun kafkaAdmin() = KafkaAdmin(mapOf("bootstrap.servers" to properties.bootstrapServers))

    @Bean
    fun topic() = NewTopic(properties.topic, 1, 1.toShort())
}