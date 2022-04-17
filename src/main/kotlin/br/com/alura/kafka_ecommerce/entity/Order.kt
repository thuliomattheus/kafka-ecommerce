package br.com.alura.kafka_ecommerce.entity

import java.math.BigDecimal

data class Order(
    val userId: String,
    val orderId: String,
    val amount: BigDecimal,
)