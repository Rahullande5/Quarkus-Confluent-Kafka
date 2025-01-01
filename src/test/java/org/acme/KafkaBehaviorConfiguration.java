package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

@ApplicationScoped
public class KafkaBehaviorConfiguration extends Configuration{

    public Configuration createConfiguration() {
        return new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(this.getClass().getClassLoader()))
                .useStoryReporterBuilder(
                        new StoryReporterBuilder()
                                .withDefaultFormats()
                                .withFormats(Format.CONSOLE, Format.TXT)
                                .withFailureTrace(true)
                                .withFailureTraceCompression(true)
                );
    }

    public InjectableStepsFactory createStepsFactory(KafkaBehaviorSteps kafkaBehaviorSteps) {
        return new InstanceStepsFactory(createConfiguration(), kafkaBehaviorSteps);
    }
}
