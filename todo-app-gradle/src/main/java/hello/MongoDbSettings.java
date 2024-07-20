package hello;

import com.mongodb.MongoClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoDbSettings {
    static class OptionsConfig {

        @Bean
        public MongoClientSettings mongoClientSettings() {
            return MongoClientSettings.builder()
                    .applyToSocketSettings(builder -> builder
                            .connectTimeout(2, TimeUnit.SECONDS)
                            .readTimeout(2, TimeUnit.SECONDS))
                    .applyToClusterSettings(builder -> builder
                            .serverSelectionTimeout(2, TimeUnit.SECONDS))
                    .build();
        }

    }
}
