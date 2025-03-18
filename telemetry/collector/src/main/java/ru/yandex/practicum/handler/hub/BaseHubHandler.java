package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.producer.KafkaEventProducer;

import java.time.Instant;

@RequiredArgsConstructor
public abstract class BaseHubHandler implements HubEventHandler {
    private final KafkaEventProducer producer;
    private String topic = "telemetry.hubs.v1";

    @Override
    public void handle(HubEventProto hubEvent) {
        producer.send(toHubEventAvro(hubEvent), hubEvent.getHubId(), mapTimestampToInstant(hubEvent), topic);
    }

    public Instant mapTimestampToInstant(HubEventProto hubEvent) {
        return Instant.ofEpochSecond(hubEvent.getTimestamp().getSeconds(), hubEvent.getTimestamp().getNanos());
    }

    public abstract HubEventAvro toHubEventAvro(HubEventProto hubEvent);
}
