package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.consumer.ConsumerRecord

class FraudDetectorService

private fun parse(record: ConsumerRecord<String, String>) {
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

private fun main() {
    KafkaService(
        FraudDetectorService::class.java.simpleName,
        "ECOMMERCE_NEW_ORDER",
        ::parse
    )
        .run()
}

