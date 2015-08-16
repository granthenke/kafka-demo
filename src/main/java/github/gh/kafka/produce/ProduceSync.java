package github.gh.kafka.produce;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.errors.ApiException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 *
 */
public class ProduceSync {

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
        properties.put("batch.size", "0"); // disable batching
        properties.put("linger.ms", "0");  // linger set to 0
        properties.put("compression.type", "none");

        // Create one producer
        final Producer<String, String> producer = new KafkaProducer<String, String>(properties, new StringSerializer(), new StringSerializer());

        final String topic = "produce-sync-topic";
        final int messageCount = 10000;

        // Send messages synchronously
        System.out.println(String.format("Starting to send %s messages", messageCount));
        for(int i = 0; i < messageCount; i++) {
            Thread.sleep(2); // Sleep to slow down execution
            final ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, String.format("I am message %s", i));
            try {
                final RecordMetadata metadata = producer.send(record).get();
                System.out.println(String.format("Message %s sent to topic %s in partition %s at offset %s", i, metadata.topic(), metadata.partition(), metadata.offset()));
            } catch (ApiException exception) {
                System.out.println(String.format("Error sending message %s", i));
                exception.printStackTrace();
            }
        }
        System.out.println(String.format("Finished sending %s messages", messageCount));
    }
}
