package br.com.alura.kafka_ecommerce.serializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.apache.kafka.common.serialization.Deserializer

class GsonDeserializer<T> : Deserializer<T> {

    companion object {
        const val TYPE_CONFIG: String = "br.com.alura.kafka_ecommerce.serializer.type_config"
    }

    private val gson: Gson = GsonBuilder().create()

    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
        configs?.get(TYPE_CONFIG).let {
            try {
                val type = Class.forName(it as String?)
            }
        }
    }

    override fun deserialize(topic: String?, data: ByteArray?): T {
        return gson.fromJson(data, type)
    }
}
