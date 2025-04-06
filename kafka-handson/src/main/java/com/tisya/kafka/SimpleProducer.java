package com.tisya.kafka;

import java.util.Properties;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SimpleProducer {

    public static void main(String[] args) {
        String topicName = "Replicated-Topic";
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka-1:9092,kafka-2:29092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("acks", "all");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 10; i++) {
            String key = "Key" + i;
            String message = "Message from KafkaApp" + i;
            producer.send(new ProducerRecord<String, String>(topicName, key, message));
        }
        producer.close();

        System.out.println("Message sent successfully");
    }
}
