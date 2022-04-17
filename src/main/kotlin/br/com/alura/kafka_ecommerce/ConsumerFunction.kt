package br.com.alura.kafka_ecommerce

import org.apache.kafka.clients.consumer.ConsumerRecord

fun interface ConsumerFunction<T> {
    fun consume(record: ConsumerRecord<String, T>)
}
