package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties

fun main() {
    var producer = KafkaProducer<String, String>(properties())
    var value = "123, 345, 1000"
    var record = ProducerRecord("ECOMMERCE_NEW_ORDER", value, value)
    producer.send(record) {
        data, exception ->
        if(exception != null) {
            exception.printStackTrace()
            return@send
        }
        println("sucesso enviando ${data.topic()} ::: partition ${data.partition()} / offset ${data.offset()} / timestamp ${data.timestamp()}")
    }.get()
}

private fun properties(): Properties {
    return Properties().apply {
        setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
        setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
    }
}
