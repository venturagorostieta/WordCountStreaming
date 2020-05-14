package com.kafka.streaming;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.kstream.KStream;

/**
 * Word count using Kafka Streaming
 * 
 * 1.- Create Topics in Kafka: inputTopic
    
    2.- Create output topic: outputTopic 
    
    3.- Run application:java -jar "jarName.jar"
    
    4.- Start producer
    
    6.- View console for Consumer:
    
    7.- Write messages in producer console and view  output in consumer console.
    
 * @author VENTURA
 *
 */
public class WordCountStreaming {
	

	public static void main(String[] args) {
		
		 Properties props = new Properties();
	        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
	        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "sandbox-hdp.hortonworks.com:6667");
	        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
	 
	        StreamsBuilder builder = new StreamsBuilder();
	        KStream<String, String> textLines = builder.stream("inputTopic");
	        KTable<String, Long> wordCounts = textLines
	            .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
	            .groupBy((key, word) -> word)
	            .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"));
	        wordCounts.toStream().to("outputTopic", Produced.with(Serdes.String(), Serdes.Long()));
	 
	        KafkaStreams streams = new KafkaStreams(builder.build(), props);
	        streams.start();
	}

}
