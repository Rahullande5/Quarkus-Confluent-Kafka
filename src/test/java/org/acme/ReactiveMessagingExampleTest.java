package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import io.smallrye.reactive.messaging.memory.InMemorySource;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class ReactiveMessagingExampleTest {

    @Inject
    @Any
    InMemoryConnector inMemoryConnector;
    private InMemorySource<String> prices;
    private InMemorySink<String> results;

    @BeforeEach
    public void setup() {
        InMemoryConnector.switchIncomingChannelsToInMemory("prices");
        InMemoryConnector.switchOutgoingChannelsToInMemory("processed-prices");
        prices = inMemoryConnector.source("prices");
        results = inMemoryConnector.sink("processed-prices");
    }

    @AfterEach
    public void cleanup() {
        results.clear();
        InMemoryConnector.clear();
    }

    @Test
    public void testSingleMessageProcessing() {
        prices.send("TestMessage1");

        waitForProcessing();

        assertThat(results.received()).hasSize(1);
        assertThat(results.received().get(0).getPayload()).isEqualTo("TestMessage1");
    }

    @Test
    public void testMultipleMessagesProcessing() {

        prices.send("Message1");
        prices.send("Message2");
        prices.send("Message3");

        waitForProcessing();

        assertThat(results.received()).hasSize(3);
        assertThat(results.received().get(0).getPayload()).isEqualTo("Message1");
        assertThat(results.received().get(1).getPayload()).isEqualTo("Message2");
        assertThat(results.received().get(2).getPayload()).isEqualTo("Message3");
    }

    @Test
    public void testNoMessageSent() {

        waitForProcessing();

        assertThat(results.received()).isEmpty();
    }

    @Test
    public void testMessageWithSpecialCharacters() {

        prices.send("Special!@#$%^&*()");

        waitForProcessing();

        assertThat(results.received()).hasSize(1);
        assertThat(results.received().get(0).getPayload()).isEqualTo("Special!@#$%^&*()");
    }

    @Test
    public void testMessageProcessingOrder() {

        prices.send("FirstMessage");
        prices.send("SecondMessage");
        prices.send("ThirdMessage");

        waitForProcessing();

        assertThat(results.received()).hasSize(3);
        assertThat(results.received().get(0).getPayload()).isEqualTo("FirstMessage");
        assertThat(results.received().get(1).getPayload()).isEqualTo("SecondMessage");
        assertThat(results.received().get(2).getPayload()).isEqualTo("ThirdMessage");
    }

    private void waitForProcessing() {
        try {
            Thread.sleep(500); // Allow time for message processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
