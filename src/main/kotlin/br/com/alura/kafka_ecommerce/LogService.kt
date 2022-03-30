package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*
import java.util.regex.Pattern

class LogService

fun main() {
    var consumer = KafkaConsumer<String, String>(properties())
    consumer.subscribe(Pattern.compile("ECOMMERCE.*"))

    while (true) {
        var records = consumer.poll(Duration.ofMillis(100))
        if (!records.isEmpty) {
            for (record in records) {
                println("----------------------------------------")
                println("LOG: ${record.topic()}")
                println(record.key())
                println(record.value())
                println(record.partition())
                println(record.offset())
            }
        }
    }
}

private fun properties(): Properties {
    return Properties().apply {
        setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
        setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
        setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService::class.java.simpleName)
    }
}
