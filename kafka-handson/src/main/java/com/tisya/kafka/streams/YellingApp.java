package com.tisya.kafka.streams;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Consumed;


public class YellingApp {
	public static void main(String[] args) {
		// Configure the Streams Application
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "yelling_app");
		props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-1:9092");
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		StreamsConfig streamingConfig = new StreamsConfig(props);

		// Initialize Serdes
		Serde<String> keySerde = Serdes.String();
		Serde<String> valueSerde = Serdes.String();

		// Develop the Topology
		// Create the Stream Builder
		StreamsBuilder builder = new StreamsBuilder();
		// Implement the source processor
		KStream<String, String> simpleFirstStream = builder.stream("src-topic", Consumed.with(keySerde, valueSerde));

		// Implement the stream processor
		KStream<String, String> upperCasedStream = simpleFirstStream.mapValues(v -> v.toUpperCase());
		// Implement the sink processor
		upperCasedStream.print(Printed.toSysOut());
		upperCasedStream.to("out-topic", Produced.with(keySerde, valueSerde));

		// Start Processing
		KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamingConfig);
		kafkaStreams.start();

		// Add Shutdown Hook
		Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
	}
}
