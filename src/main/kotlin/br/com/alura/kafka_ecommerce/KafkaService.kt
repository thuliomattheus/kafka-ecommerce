package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.io.Closeable
import java.time.Duration
import java.util.UUID
import java.util.Properties

class KafkaService(
    private val groupId: String,
    private val topic: String,
    private val parse: ConsumerFunction,
): Closeable {

    private lateinit var consumer: KafkaConsumer<String, String>

    companion object {
        private fun properties(groupId: String): Properties {
            return Properties().apply {
                setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
                setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
                setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
                setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString())
                setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId)
                setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
            }
        }
    }

    fun run() {
        consumer = KafkaConsumer<String, String>(properties(groupId))
        consumer.subscribe(mutableListOf(topic))
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100))
            if (!records.isEmpty) {
                for (record in records) {
                    parse.consume(record)
                }
            }
        }
    }

    override fun close() {
        consumer.close()
    }
}
