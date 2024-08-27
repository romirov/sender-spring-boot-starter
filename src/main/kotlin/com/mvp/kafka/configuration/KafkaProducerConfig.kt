package com.mvp.kafka.configuration

import com.mvp.kafka.prop.KafkaSenderProperties
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
@AutoConfiguration
@EnableConfigurationProperties(KafkaSenderProperties::class)
@ConditionalOnProperty(prefix = "spring.kafka", name = ["enable"], havingValue = "true")
class KafkaProducerConfig(
    private val properties: KafkaSenderProperties
) {
    @Bean
    fun producerFactory(): ProducerFactory<String, String>{
        val config = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to properties.bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.ACKS_CONFIG to properties.producer.acks,
            ProducerConfig.RETRIES_CONFIG to properties.producer.retries,
            ProducerConfig.CLIENT_ID_CONFIG to properties.producer.clientId
        )
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun kafkaTemplate() = KafkaTemplate(producerFactory())
}