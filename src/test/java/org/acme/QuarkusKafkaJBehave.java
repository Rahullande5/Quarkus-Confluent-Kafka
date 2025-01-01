package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.steps.InjectableStepsFactory;

import java.util.List;

@QuarkusTest
@UsingSteps(instances = KafkaBehaviorSteps.class)
public class QuarkusKafkaJBehave extends JUnitStories {

    private final KafkaBehaviorConfiguration kafkaBehaviorConfiguration = new KafkaBehaviorConfiguration();
    private final KafkaBehaviorSteps kafkaBehaviorSteps = new KafkaBehaviorSteps();


    @Override
    public Configuration configuration() {
        return kafkaBehaviorConfiguration.createConfiguration();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        return kafkaBehaviorConfiguration.createStepsFactory(kafkaBehaviorSteps);
    }

    @Override
    public List<String> storyPaths() {
        return List.of("stories/kafka-detail.story");
    }
}
