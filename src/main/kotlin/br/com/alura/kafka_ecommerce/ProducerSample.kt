package br.com.alura.kafka_ecommerce

import java.util.UUID

fun main() {
    // A scope function `use` funciona como um try-with-resources e "fecha" a conexão do producer após
    // o seu uso ou o lançamento de alguma exceção
    MyKafkaProducer().use { producer ->
        val key = UUID.randomUUID().toString()
        val value = "$key, 345, 1000"
        producer.send("ECOMMERCE_NEW_ORDER", key, value)

        val email = "Thank you for your order! We are in processing stage!"
        producer.send("ECOMMERCE_SEND_EMAIL", key, email)
    }
}
