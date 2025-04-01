package ru.yandex.practicum.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.handler.snapshot.SnapshotHandler;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnapshotProcessor {
    private final Consumer<String, SensorsSnapshotAvro> snapshotConsumer;
    private final SnapshotHandler snapshotHandler;
    private final Duration CONSUME_ATTEMPT_TIMEOUT = Duration.ofMillis(1000);
    private final String topic = "telemetry.snapshots.v1";

    public void start() {
        try {
            snapshotConsumer.subscribe(List.of(topic));
            Runtime.getRuntime().addShutdownHook(new Thread(snapshotConsumer::wakeup));

            while (true) {
                try {
                    ConsumerRecords<String, SensorsSnapshotAvro> records = snapshotConsumer.poll(CONSUME_ATTEMPT_TIMEOUT);

                    for (ConsumerRecord<String, SensorsSnapshotAvro> record : records) {
                        log.info("{}: полученное сообщение из kafka: {}", SnapshotProcessor.class.getSimpleName(), record);
                        SensorsSnapshotAvro snapshotAvro = record.value();
                        log.info("снимок состояния умного дома: {}", snapshotAvro.getClass().getName());
                        snapshotHandler.handleSnapshot(snapshotAvro);
                    }

                    snapshotConsumer.commitSync();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            }
        } catch (WakeupException ignored) {
        } catch (Exception e) {
            log.error("Ошибка чтения данных из топика {}", topic);
        } finally {
            try {
                snapshotConsumer.commitSync();
            } finally {
                snapshotConsumer.close();
            }
        }
    }
}

