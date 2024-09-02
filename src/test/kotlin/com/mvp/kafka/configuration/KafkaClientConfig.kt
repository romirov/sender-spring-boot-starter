package com.mvp.kafka.configuration

import com.mvp.common.dao.IMessage
import com.mvp.kafka.configuration.prop.KafkaConsumerProperties
import com.mvp.kafka.prop.KafkaSenderProperties
import com.mvp.kafka.serialize.KafkaSerializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.Acknowledgment

@TestConfiguration
@EnableConfigurationProperties(KafkaSenderProperties::class, KafkaConsumerProperties::class)
class KafkaClientConfig(
    val senderProperties: KafkaSenderProperties,
    val consumerProperties: KafkaConsumerProperties
) {
    @Bean
    fun consumerConfig() = mapOf(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to senderProperties.bootstrapServers,
        ConsumerConfig.GROUP_ID_CONFIG to consumerProperties.properties.groupId,
        ConsumerConfig.CLIENT_ID_CONFIG to consumerProperties.properties.clientId,
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to consumerProperties.properties.keyDeserializer,
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to consumerProperties.properties.valueDeserializer,
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to consumerProperties.properties.autoOffsetReset
    )

    @Bean
    fun consumerFactory() =
        DefaultKafkaConsumerFactory<String, String>(consumerConfig())

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> =
        ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = consumerFactory()
            setConcurrency(senderProperties.numPartitions)
            containerProperties.pollTimeout = 1000
        }

    @Bean
    @KafkaListener(topics = ["#{senderProperties.topic}"], containerFactory = "#{kafkaListenerContainerFactory}")
    fun messageListener(message: ConsumerRecord<String, String>, ack: Acknowledgment){
        val messageDeserialized = KafkaSerializer.deserialize(message.value())
        kafkaRepo().add(messageDeserialized)
        ack.acknowledge()
    }

    @Bean
    fun kafkaRepo(): MutableList<IMessage> = mutableListOf()
}