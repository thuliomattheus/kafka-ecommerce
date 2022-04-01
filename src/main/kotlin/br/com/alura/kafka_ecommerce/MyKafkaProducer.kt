package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import java.io.Closeable
import java.util.Properties

class MyKafkaProducer(
    private val producer: KafkaProducer<String, String> = KafkaProducer(properties()),
): Closeable {

    fun send(topic: String, key: String, value: String) {
        val record = ProducerRecord(topic, key, value)
        producer.send(record, callback()).get()
    }

    private fun callback(): (RecordMetadata, Exception?) -> Unit = { data, exception ->
        if(exception != null)
            exception.printStackTrace()
        else
            println("sucesso enviando ${data.topic()} ::: partition ${data.partition()} / offset ${data.offset()} / timestamp ${data.timestamp()}")
    }

    override fun close()= producer.close()
}

private fun properties() = Properties().apply {
        setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
        setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
        setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.canonicalName)
    }
