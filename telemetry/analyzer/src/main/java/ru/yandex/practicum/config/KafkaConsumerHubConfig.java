package ru.yandex.practicum.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerHubConfig {
    private final KafkaPropertiesConfig kafkaPropertiesConfig;

    @Bean
    public KafkaConsumer<String, HubEventAvro> getHubEventConsumer() {
        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertiesConfig.getBootstrapService());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaPropertiesConfig.getConsumerHubGroupId());
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, kafkaPropertiesConfig.getConsumerHubClientIdConfig());
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaPropertiesConfig.getConsumerHubKeyDeserializer());
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaPropertiesConfig.getConsumerHubValueDeserializer());

        return new KafkaConsumer<>(config);
    }
}
