package by.mchs.e112.incident.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic incidentCreatedTopic() {
        return TopicBuilder.name("incident.created").partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic incidentUpdatedTopic() {
        return TopicBuilder.name("incident.updated").partitions(3).replicas(1).build();
    }
}
