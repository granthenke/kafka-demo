package github.gh.kafka.produce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.List;
import java.util.Properties;

/**
 * Note: This is more interesting with a topic that has 2 or more partitions.
 * You can achieve this by either
 *    - configuring the default partition count (num.partitions=2) on the broker
 *    - or manually creating the topic
 *       ex: bin/kafka-topics.sh --create --zookeeper localhost:2181 --topic produce-manual-partition-topic --partitions 2 --replication-factor 1
 */
public class ProduceManualPartition {

    public static void main(String[] args) throws Exception {
        String bootstrap = "localhost:9092";
        if(args.length < 1) {
            System.out.println("Bootstrap argument was not passed. Defaulting to localhost:9092");
        } else {
            bootstrap = args[0];
        }

        // See configuration options here: http://kafka.apache.org/documentation.html#newproducerconfigs
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrap);
        // Message delivery & batching configuration
        properties.put("acks", "-1");
        properties.put("retries", "3");
        properties.put("batch.size", "16384");
        properties.put("linger.ms", "10");
        properties.put("compression.type", "none");

        // Create one producer
        final Producer<String, String> producer = new KafkaProducer<String, String>(properties, new StringSerializer(), new StringSerializer());

        final String topic = "produce-manual-partition-topic";
        final int messageCount = 10000;

        // Send messages asynchronously
        System.out.println(String.format("Starting to send %s messages", messageCount));
        for(int i = 0; i < messageCount; i++) {
            Thread.sleep(2); // Sleep to slow down execution
            List<PartitionInfo> partitionInfos = producer.partitionsFor(topic);
            for(PartitionInfo partitionInfo: partitionInfos) {
                // Send message to every divisible partition+1
                if(i % (partitionInfo.partition() + 1) == 0) {
                    System.out.println(String.format("Sending message %s to partition %s", i, partitionInfo.partition()));
                    final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, partitionInfo.partition(), null, String.format("I am message %s", i));
                    producer.send(record);
                }
            }
        }
        System.out.println(String.format("Finished sending %s messages", messageCount));
    }
}
