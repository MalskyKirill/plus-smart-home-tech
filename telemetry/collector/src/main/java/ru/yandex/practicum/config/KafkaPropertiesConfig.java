package ru.yandex.practicum.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class KafkaPropertiesConfig {
    @Value("${kafka.producer.bootstrap-server}")
    private String bootstrapService;
    @Value("${kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${kafka.producer.value-serializer}")
    private String valueSerializer;
}
