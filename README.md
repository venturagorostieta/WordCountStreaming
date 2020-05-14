# WordCountStreaming
Word counter using Kafka Streaming with Java


Word Counter Streaming:
Create topics:
/usr/hdp/current/kafka-broker/bin/kafka-topics.sh  --create --zookeeper sandbox-hdp.hortonworks.com:2181 --replication-factor 1 --partitions 1 --topic inputTopic

/usr/hdp/current/kafka-broker/bin/kafka-topics.sh  --create --zookeeper sandbox-hdp.hortonworks.com:2181 --replication-factor 1 --partitions 1 --topic outputTopic

Create Producer:
/usr/hdp/current/kafka-broker/bin/kafka-console-producer.sh --broker-list sandbox-hdp.hortonworks.com:6667 --topic inputTopic


Create Consumer:
/usr/hdp/current/kafka-broker/bin/kafka-console-consumer.sh --bootstrap-server sandbox-hdp.hortonworks.com:6667 --topic outputTopic \  --from-beginning \    --formatter kafka.tools.DefaultMessageFormatter \    --property print.key=true \     --property print.value=true \     --property key.deserializer=org.apache.kafka.common.serialization.StringDeserializer \  --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer


Run java application and  write messages in producer console and see result in consumer console.