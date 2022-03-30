package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Properties
import java.util.UUID

fun main() {
    var producer = KafkaProducer<String, String>(properties())

    /*
    producer.send(record) { data, exception ->
        if(exception != null) {
            return@send
        }
        println("sucesso enviando ${data.topic()} ::: partition ${data.partition()} / offset ${data.offset()} / timestamp ${data.timestamp()}")
    }.get()
     */

    var key = UUID.randomUUID().toString()
    var value = "$key, 345, 1000"
    var record = ProducerRecord("ECOMMERCE_NEW_ORDER", key, value)
    var email = "Thank you for your order! We are in processing stage!"
    var emailRecord = ProducerRecord("ECOMMERCE_SEND_EMAIL", key, email)

    producer.send(record, callback()).get()
    producer.send(emailRecord, callback()).get()
}

private fun callback(): (RecordMetadata, Exception?) -> Unit = { data, exception ->
    if(exception != null)
        exception.printStackTrace()
    else
        println("sucesso enviando ${data.topic()} ::: partition ${data.partition()} / offset ${data.offset()} / timestamp ${data.timestamp()}")
}

private fun properties(): Properties {
    return Properties().apply {
        setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
        setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
    }
}
