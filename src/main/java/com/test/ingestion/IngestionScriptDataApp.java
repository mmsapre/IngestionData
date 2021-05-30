package com.test.ingestion;

import com.test.ingestion.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;


@SpringBootApplication(scanBasePackages = {"com.test.ingestion"}, exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties({AppProperties.class})
@EnableRedisRepositories(basePackages = "com.test.ingestion.repository")
public class IngestionScriptDataApp {

    private static final Logger LOG = LoggerFactory.getLogger(IngestionScriptDataApp.class);

    private static ConfigurableApplicationContext context;
    private final Environment env;

    public IngestionScriptDataApp(final Environment env) {
        this.env = env;
    }


    public final static ConfigurableApplicationContext getContext() {
        return context;
    }

    public final void setContext(final ConfigurableApplicationContext context) {
        IngestionScriptDataApp.context = context;
    }

    public static void main(final String[] args) throws Exception {
        try {
            final SpringApplication app = new SpringApplication(IngestionScriptDataApp.class);
            app.run(args);
            //final Environment env = app.run(args).getEnvironment();
        } catch (Exception e) {
            LOG.error("Exception while starting app as: ", e);
        }
    }

    @PostConstruct
    public void initApplication() {
        final Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

    }

}
