package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.consumer.ConsumerRecord
import java.util.regex.Pattern

class LogService

fun main() {
    KafkaService<String>(
        groupId = LogService::class.java.simpleName,
        topicPattern = Pattern.compile("ECOMMERCE.*"),
        parse = ::parse
    )
        .run()
}

private fun parse (record: ConsumerRecord<String, String>) {
    println("----------------------------------------")
    println("LOG: ${record.topic()}")
    println(record.key())
    println(record.value())
    println(record.partition())
    println(record.offset())
}
