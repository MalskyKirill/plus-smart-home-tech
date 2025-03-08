package ru.yandex.practicum.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventProducer implements AutoCloseable {
    private final Producer<String, SpecificRecordBase> eventProducer;

    public void send(SpecificRecordBase message, String hubId, Instant timestamp, String topic) {
        try {
            ProducerRecord<String, SpecificRecordBase> record = new ProducerRecord<>(topic, null, timestamp.toEpochMilli(), hubId, message);
            eventProducer.send(record, (metadata, exception) -> {
                if(exception != null) {
                    log.error("Ошибка при отправке сообщения", exception);
                }
            });
        } catch (Exception e) {
            log.error("Ошибка при отправке сообщения", e);
        }
    }

    @Override
    public void close() {
        try {
            eventProducer.flush();
            eventProducer.close(Duration.ofSeconds(10));
        } catch (Exception e) {
            log.error("Ошибка при отправке сообщения", e);
        }
    }
}
