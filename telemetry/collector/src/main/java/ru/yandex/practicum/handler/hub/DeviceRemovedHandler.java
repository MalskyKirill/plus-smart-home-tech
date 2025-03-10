package ru.yandex.practicum.handler.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.model.hub.DeviceRemoveEvent;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class DeviceRemovedHandler extends BaseHubHandler{
    public DeviceRemovedHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SpecificRecordBase toHubEventAvro(HubEvent hubEvent) {
        DeviceRemoveEvent event = (DeviceRemoveEvent) hubEvent;

        return HubEventAvro.newBuilder()
            .setHubId(hubEvent.getHubId())
            .setTimestamp(hubEvent.getTimestamp())
            .setPayload(new DeviceRemovedEventAvro(event.getId()))
            .build();
    }
}
