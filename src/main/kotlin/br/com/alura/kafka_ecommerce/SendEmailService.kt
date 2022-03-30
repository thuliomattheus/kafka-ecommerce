package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.consumer.ConsumerRecord

class SendEmailService

private fun main() {
    KafkaService(
        SendEmailService::class.java.simpleName,
        "ECOMMERCE_SEND_EMAIL",
        ::parse
    )
        .run()
}

private fun parse(record: ConsumerRecord<String, String>) {
    println("----------------------------------------")
    println("Sending email")
    println(record.key())
    println(record.value())
    println(record.partition())
    println(record.offset())
    try {
        Thread.sleep(1000)
    } catch (exc: InterruptedException) {
        exc.printStackTrace()
    }
    println("Email send")
}
