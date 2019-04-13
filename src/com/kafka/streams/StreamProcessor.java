/**
 * 
 */
package com.kafka.streams;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;

import com.kafka.models.Orders;

/**
 * @author Abin K. Antony
 * 13-Apr-2019
 * @version 1.0
 */
public class StreamProcessor {
    public static void main(String[] args) {
	String inputTopic = "ecommerce";
	String bootstrapServer = "172.30.66.108:9092";
	
	Properties streamsConfig = new Properties();
	streamsConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka_ecommerce");
	streamsConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
	streamsConfig.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	streamsConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	
	StreamsBuilder builder = new StreamsBuilder();
	KStream<String, String> input = builder.stream(inputTopic);
	
	//map inputstream and create custom key
	input.map(new KeyValueMapper<String, String, KeyValue<String, Orders>>() {
	    @Override
	    public KeyValue<String, Orders> apply(String key, String value) {
	        Orders order = new Orders(value);
		return new KeyValue<String, Orders>(order.getOrderId(), order);
	    }
	});
	
	
	
	
	KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);
	kafkaStreams.cleanUp();
	kafkaStreams.start();
    }
    
}
