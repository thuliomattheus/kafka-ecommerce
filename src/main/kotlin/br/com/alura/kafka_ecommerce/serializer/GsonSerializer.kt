package br.com.alura.kafka_ecommerce.serializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.kafka.common.serialization.Serializer

class GsonSerializer<T>(): Serializer<T> {

    private val gson: Gson = GsonBuilder().create()

    override fun serialize(topic: String?, data: T): ByteArray = gson.toJson(data).toByteArray()
}