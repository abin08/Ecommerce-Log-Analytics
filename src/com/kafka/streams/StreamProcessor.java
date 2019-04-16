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
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;

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
	
//	total number of input streams
	KTable<String, Long> totalCount = input.map(new KeyValueMapper<String, String, KeyValue<String,String>>() {
	   @Override
	    public KeyValue<String, String> apply(String key, String value) {
	       return new KeyValue<String, String>("total", value);
	    } 
	})
	.groupByKey()
	.count();
	
//	map inputstream and create custom key
	KStream<String, Orders> sourceStream = input.map(new KeyValueMapper<String, String, KeyValue<String, Orders>>() {
	    @Override
	    public KeyValue<String, Orders> apply(String key, String value) {
	        Orders order = new Orders(value);
		return new KeyValue<String, Orders>(order.getOrderId(), order);
	    }
	});
	
//	total number of Male
	KStream<String, Orders> maleCustomer = sourceStream.filter(new Predicate<String, Orders>() {
	    @Override
	    public boolean test(String key, Orders value) {
	        return value.getCustomerGender().equalsIgnoreCase("Male");
	    }
	});
	KTable<String, Long> maleCustomerCount = maleCustomer.map(new KeyValueMapper<String, Orders, KeyValue<String,String>>() {
	    @Override
	    public KeyValue<String, String> apply(String key, Orders value) {
	        return new KeyValue<String, String>("total_Male", value.getCustomerGender());
	    }
	})
	.groupByKey()
	.count();
	
//	total number of Female
	KStream<String, Orders> femaleCustomer = sourceStream.filter(new Predicate<String, Orders>() {
	    @Override
	    public boolean test(String key, Orders value) {
	        return value.getCustomerGender().equalsIgnoreCase("Female");
	    }
	});
	KTable<String, Long> femaleCustomerCount = femaleCustomer.map(new KeyValueMapper<String, Orders, KeyValue<String,String>>() {
	    @Override
	    public KeyValue<String, String> apply(String key, Orders value) {
	        return new KeyValue<String, String>("total_Female", value.getCustomerGender());
	    }
	})
	.groupByKey()
	.count();
	
//	find male customer percentage
	KTable<String, String> malePercentage = maleCustomerCount.join(totalCount, (maleCount,totalCounts) -> ((maleCount.longValue()/totalCounts.longValue())*100) + "");
	
	malePercentage.toStream().to("malePercentage");
		
	/*.toStream()
	.map(new KeyValueMapper<String, Long, KeyValue<String,String>>() {
	    @Override
	    public KeyValue<String, String> apply(String key, Long value) {
	        return new KeyValue<String, String>("Male", value+"%");
	    }
	}).foreach((key,value) -> {System.out.println(key+" "+value);});*/
	
//	find female customer percentage
	KTable<String, String> femalePercentage = femaleCustomerCount.join(totalCount, (femaleCount,totalCounts) -> ((femaleCount.longValue()/totalCounts.longValue())*100) + "");
	/*.toStream()
	.map(new KeyValueMapper<String, Long, KeyValue<String,String>>() {
	    @Override
	    public KeyValue<String, String> apply(String key, Long value) {
	        return new KeyValue<String, String>("Female", value+"%");
	    }
	}).foreach((key,value) -> {System.out.println(key+" "+value);});*/
	
	
	
	malePercentage.join(femalePercentage, (male, female) -> "Male: "+male +"%,"+"Female: "+female+"%" )
	.toStream()
	.to("salesByGender", Produced.with(Serdes.String(), Serdes.String()));
	
	
//	maleCustomerCount.foreach((key,value) -> {System.out.println(key+" "+value);});
//	femaleCustomerCount.foreach((key,value) -> {System.out.println(key+" "+value);});
	
	KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);
	kafkaStreams.cleanUp();
	kafkaStreams.start();
    }
    
}
