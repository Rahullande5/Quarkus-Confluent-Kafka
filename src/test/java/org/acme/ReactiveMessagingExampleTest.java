package smallrye;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import io.smallrye.reactive.messaging.memory.InMemorySink;
import io.smallrye.reactive.messaging.memory.InMemorySource;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class ReactiveMessagingExampleTest {
    // 1. Switch the channels to the in-memory connector:
    @BeforeAll
    public static void switchMyChannels() {
        InMemoryConnector.switchIncomingChannelsToInMemory("prices");
        InMemoryConnector.switchOutgoingChannelsToInMemory("processed-prices");
    }

    // 2. Don't forget to reset the channel after the tests:
    @AfterAll
    public static void revertMyChannels() {
        InMemoryConnector.clear();
    }

    // 3. Inject the in-memory connector in your test,
    // or use the bean manager to retrieve the instance
    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    void test() {
        // 4. Retrieves the in-memory source to send message
        InMemorySource<String> prices = connector.source("prices");
        // 5. Retrieves the in-memory sink to check what is received
        InMemorySink<String> results = connector.sink("processed-prices");

        InMemorySource<String> send = prices.send("Hello World");
        assertThat(send).isNotNull();


    }
}
