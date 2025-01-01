Scenario: Produce and consume Kafka message
Given a message to send HelloKafka
When the producer sends the message
Then the consumer should receive the message
