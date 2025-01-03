package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class KafkaConsumer {
    @Incoming("prices")
    public void consumeMessage(String message) {
        System.out.println("Received: " + message);
    }
}
