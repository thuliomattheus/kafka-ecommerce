package br.com.alura.kafka_ecommerce

import br.com.alura.kafka_ecommerce.entity.Order
import java.math.BigDecimal
import java.util.UUID

fun main() {
    // A scope function `use` funciona como um try-with-resources e "fecha" a conexão do producer após
    // o seu uso ou o lançamento de alguma exceção
    MyKafkaProducer<Order>().use { producer ->
        val order = Order(
            userId = UUID.randomUUID().toString(),
            orderId = UUID.randomUUID().toString(),
            amount = BigDecimal(Math.random() * 100.0 + 1),
        )
        producer.send("ECOMMERCE_NEW_ORDER", order.userId, order)
    }

    MyKafkaProducer<String>().use { producer ->
        val userId = UUID.randomUUID().toString()
        val email = "Thank you for your order! We are in processing stage!"
        producer.send("ECOMMERCE_SEND_EMAIL", userId, email)
    }

}
