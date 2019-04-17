/**
 * 
 */
package com.kafka.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Aggregator;
import org.apache.kafka.streams.kstream.Initializer;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;

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
	KTable<String, Long> maleCustomerCount = sourceStream.filter(new Predicate<String, Orders>() {
	    @Override
	    public boolean test(String key, Orders value) {
	        return value.getCustomerGender().equalsIgnoreCase("Male");
	    }
	}).map(new KeyValueMapper<String, Orders, KeyValue<String,String>>() {
	    @Override
	    public KeyValue<String, String> apply(String key, Orders value) {
	        return new KeyValue<String, String>("total", value.getCustomerGender());
	    }
	})
	.groupByKey()
	.count();
	
//	total number of Female
	KTable<String, Long> femaleCustomerCount = sourceStream.filter(new Predicate<String, Orders>() {
	    @Override
	    public boolean test(String key, Orders value) {
	        return value.getCustomerGender().equalsIgnoreCase("Female");
	    }
	}).map(new KeyValueMapper<String, Orders, KeyValue<String,String>>() {
	    @Override
	    public KeyValue<String, String> apply(String key, Orders value) {
	        return new KeyValue<String, String>("total", value.getCustomerGender());
	    }
	})
	.groupByKey()
	.count();
	
//	find male customer percentage
	KTable<String, String> malePercentage = maleCustomerCount.join(totalCount, (maleCount,totalCounts)
		->((maleCount.doubleValue()/totalCounts.doubleValue()) * 100)+"%");
	
//	find female customer percentage
	KTable<String, String> femalePercentage = femaleCustomerCount.join(totalCount, (femaleCount,totalCounts) 
		-> ((femaleCount.doubleValue()/totalCounts.doubleValue()) * 100) + "%");
	
	
	malePercentage.join(femalePercentage, (male, female) -> "Male: "+male +","+"Female: "+female )
	.toStream()
	.to("salesByGender" , Produced.with(Serdes.String(), Serdes.String()));
//	.foreach((key,value) -> {System.out.println("Sales by Gender : " + key+" "+value);});
	
//	total revenue
	KTable<String, Double> totalRevenue = sourceStream.map(new KeyValueMapper<String, Orders, KeyValue<String,Double>>() {
	    @Override
	    public KeyValue<String, Double> apply(String key, Orders value) {
	        return new KeyValue<String, Double>("total", value.getTaxfulTotalPrice());
	    }
	}).groupByKey(Serialized.with(Serdes.String(), Serdes.Double()))
	.aggregate(new Initializer<Double>() {
	    @Override
	    public Double apply() {
		return 0.0;
	    }
	}, new Aggregator<String, Double, Double>() {
	    @Override
	    public Double apply(String key, Double value, Double aggregate) {
		return aggregate+ value;
	    }
	}, Materialized.with(Serdes.String(), Serdes.Double()));
	
	totalRevenue.toStream()
	.to("totalRevenue", Produced.with(Serdes.String(), Serdes.Double()));
//	.foreach((key,value) -> {System.out.println("Total Revenue : "+key+" "+value);});
	
//	average sales price
	totalRevenue.join(totalCount, (sum,count) -> sum / count.doubleValue())
	.toStream()
	.to("averageSalesPrice",Produced.with(Serdes.String(), Serdes.Double()));
//	.foreach((key,value) -> {System.out.println("Average sales price : "+key+" "+value);});
	
//	total sold quantity
	KTable<String, Integer> totalQuantity = sourceStream.map(new KeyValueMapper<String, Orders, KeyValue<String,Integer>>() {
	    @Override
	    public KeyValue<String, Integer> apply(String key, Orders value) {
	        return new KeyValue<String, Integer>("total", value.getTotalQuantity());
	    }
	}).groupByKey(Serialized.with(Serdes.String(), Serdes.Integer()))
	.aggregate(new Initializer<Integer>() {
	    @Override
	    public Integer apply() {
		return 0;
	    }
	}, new Aggregator<String, Integer, Integer>() {
	    @Override
	    public Integer apply(String key, Integer value, Integer aggregate) {
		return aggregate+ value;
	    }
	}, Materialized.with(Serdes.String(), Serdes.Integer()));
	
//	average sold quantity
	totalQuantity.join(totalCount, (sum, count) -> sum.intValue() / count.intValue())
	.toStream()
	.to("averageSoldQuantity", Produced.with(Serdes.String(), Serdes.Integer()));
//	.foreach((key,value) -> {System.out.println("Average sold quantity : "+key+" "+value);});
	
//	sales by category
	sourceStream.flatMap(new KeyValueMapper<String, Orders, Iterable<KeyValue<String,Integer>>>() {
	    @Override
	    public Iterable<KeyValue<String, Integer>> apply(String key, Orders value) {
	        
		String[] categories = value.getCategory();
		List<KeyValue<String, Integer>> result = new ArrayList<>(categories.length);
		String orderDate = value.getOrderDate().split(",")[0].replace("\"", "");
		for(String category : categories) {
		    result.add(new KeyValue<String, Integer>(category + "_" + orderDate, value.getTotalQuantity()));
		}
		return result;
	    }
	})
	.groupByKey(Serialized.with(Serdes.String(), Serdes.Integer()))
	.aggregate(new Initializer<Integer>() {
	    @Override
	    public Integer apply() {
		return 0;
	    }
	}, new Aggregator<String, Integer, Integer>() {
	    @Override
	    public Integer apply(String key, Integer value, Integer aggregate) {
		return aggregate+ value;
	    }
	}, Materialized.with(Serdes.String(), Serdes.Integer()))
	.toStream()
	.to("salesByCategory", Produced.with(Serdes.String(), Serdes.Integer()));
//	.foreach((key,value) -> {System.out.println("Sales By category : "+key+" "+value);});
	
	KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);
	kafkaStreams.cleanUp();
	kafkaStreams.start();
    }
    
}
