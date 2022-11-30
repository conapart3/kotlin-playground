package kafka

import java.time.Duration
import java.time.LocalDateTime
import java.util.Properties
import kotlin.concurrent.fixedRateTimer
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerRecord

fun main() {
    val producer = KafkaService().createProducer()
    val consumer = KafkaService().createConsumer()

    val topic = "conal-topic"
    producer.produceMessages(topic)
    consumer.consumeMessages(topic)


}

class KafkaService {
    fun createProducer(): Producer<String, String> {
        val props = Properties()
        props["bootstrap.servers"] = "localhost:9092"
        props["acks"] = "all"
        props["retries"] = 0
        props["linger.ms"] = 1
        props["key.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        props["value.serializer"] = "org.apache.kafka.common.serialization.StringSerializer"
        return KafkaProducer(props)
    }

    fun createConsumer(): Consumer<String, String> {
        val props = Properties()
        props.setProperty("bootstrap.servers", "localhost:9092")
        props.setProperty("group.id", "test")
        props.setProperty("enable.auto.commit", "true")
        props.setProperty("auto.commit.interval.ms", "1000")
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        return KafkaConsumer(props)
    }


}
fun Producer<String, String>.produceMessages(topic: String) {
    fixedRateTimer(daemon = true, period = Duration.ofSeconds(2).toMillis()) {
        val time = LocalDateTime.now()
        val message = ProducerRecord(
            topic, // topic
            time.toString(), // key
            "Message sent at ${LocalDateTime.now()}" // value
        )
        send(message)
    }
}

fun Consumer<String, String>.consumeMessages(topic: String) {
    subscribe(listOf(topic))
    while (true) {
        val messages: ConsumerRecords<String, String> = poll(Duration.ofMillis(5000))
        for (message in messages) {
            println("Consumer reading message: ${message.value()}")
        }
        commitAsync()
    }
}