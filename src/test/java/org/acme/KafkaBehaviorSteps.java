package org.acme;

import io.restassured.RestAssured;
import jakarta.enterprise.context.ApplicationScoped;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Then;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@ApplicationScoped
public class KafkaBehaviorSteps {

    private String message="Hello Kafka";
    private final BlockingQueue<String> receivedMessages = new LinkedBlockingQueue<>();

    @Given("a message to send {string}")
    public void givenAMessageToSend(String msg) {
        this.message = msg;
    }

    @When("the producer sends the message")
    public void whenTheProducerSendsTheMessage() {
        RestAssured.given()
                .contentType("text/plain")
                .body(message)
                .post("/kafka/produce")
                .then()
                .statusCode(200);
    }

    @Then("the consumer should receive the message")
    public void thenTheConsumerShouldReceiveTheMessage() throws InterruptedException {
        String received = receivedMessages.poll(10, java.util.concurrent.TimeUnit.SECONDS);
        assertThat("The consumer did not receive the expected message!", received, Matchers.containsString(message));
    }

    // Simulated consumer (hooked into KafkaConsumer in your app)
    public void receiveMessage(String message) {
        receivedMessages.offer(message);
    }
}
