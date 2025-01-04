package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class KafkaConsumer {

    @Inject
    KafkaProducer producer;

    @Incoming("prices")
    public void consumeMessage(String message) {
        // Process and send the message to the producer
        producer.sendMessage(message);
    }
}
