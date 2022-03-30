package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*
import java.util.regex.Pattern

class FraudDetectorService

fun main() {
    var consumer = KafkaConsumer<String, String>(properties())
    consumer.subscribe(Pattern.compile("ECOMMERCE_NEW_ORDER"))

    while (true) {
        var records = consumer.poll(Duration.ofMillis(100))
        if (!records.isEmpty) {
            for (record in records) {
                println("----------------------------------------")
                println("Processing new order, checking for fraud")
                println(record.key())
                println(record.value())
                println(record.partition())
                println(record.offset())
                try {
                    Thread.sleep(500)
                } catch (exc: InterruptedException) {
                    exc.printStackTrace()
                }
                println("Order processed")
            }
        }
    }
}

private fun properties(): Properties {
    return Properties().apply {
        setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
        setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.canonicalName)
        setProperty(ConsumerConfig.GROUP_ID_CONFIG, FraudDetectorService::class.java.simpleName)
        setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
    }
}
