package br.com.alura.kafka_ecommerce

import br.com.alura.kafka_ecommerce.serializer.GsonDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.io.Closeable
import java.time.Duration
import java.util.UUID
import java.util.Properties
import java.util.regex.Pattern

class KafkaService<T> private constructor(
    groupId: String,
    private val parse: ConsumerFunction<T>,
): Closeable {

    private val consumer: KafkaConsumer<String, T> = KafkaConsumer(properties(groupId))

    constructor(
        groupId: String,
        parse: ConsumerFunction<T>,
        topic: String,
    ) : this(groupId, parse) {
        consumer.subscribe(mutableListOf(topic))
    }

    constructor(
        groupId: String,
        parse: ConsumerFunction<T>,
        topicPattern: Pattern,
    ) : this(groupId, parse) {
        consumer.subscribe(topicPattern)
    }

    private fun properties(groupId: String): Properties {
        return Properties().apply {
            setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
            setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
            setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer::class.java.canonicalName)
            setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString())
            setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId)
            setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
            setProperty(GsonDeserializer.TYPE_CONFIG, String::class.simpleName)
        }
    }

    fun run() {
        while (true) {
            val records = consumer.poll(Duration.ofMillis(100))
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
