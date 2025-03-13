package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.producer.KafkaEventProducer;

@RequiredArgsConstructor
public abstract class BaseHubHandler implements HubEventHandler {
    private final KafkaEventProducer producer;
    private String topic = "telemetry.hubs.v1";

    @Override
    public void handle(HubEvent hubEvent) {
        producer.send(toHubEventAvro(hubEvent), hubEvent.getHubId(), hubEvent.getTimestamp(), topic);
    }

    public abstract SpecificRecordBase toHubEventAvro(HubEvent hubEvent);
}
