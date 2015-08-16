package github.gh.kafka.produce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 *
 */
public class ProduceMultiTopic {

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

        final String topicEven = "produce-multi-even-topic";
        final String topicOdd = "produce-multi-odd-topic";
        final int messageCount = 10000;

        // Send messages asynchronously
        System.out.println(String.format("Starting to send %s messages", messageCount));
        for(int i = 0; i < messageCount; i++) {
            Thread.sleep(2); // Sleep to slow down execution
            // Send even messages to topicEven and odd messages to topicOdd
            if(i % 2 == 0) {
                System.out.println(String.format("Sending even message: %s", i));
                final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicEven, String.format("I am even message %s", i));
                producer.send(record);
            } else {
                System.out.println(String.format("Sending odd message: %s", i));
                final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicOdd, String.format("I am odd message %s", i));
                producer.send(record);
            }
        }
        System.out.println(String.format("Finished sending %s messages", messageCount));
    }
}
