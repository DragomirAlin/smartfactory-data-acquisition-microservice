package ro.dragomiralin.data.acquisition.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = { EmbeddedMongoAutoConfiguration.class })
public class MongoConfig{
}