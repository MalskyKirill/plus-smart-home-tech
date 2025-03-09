package ru.yandex.practicum.handler.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.model.enums.DeviceType;
import ru.yandex.practicum.model.hub.DeviceAddedEvent;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class DeviceAddedHandler extends BaseHubHandler{
    public DeviceAddedHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SpecificRecordBase toHubEventAvro(HubEvent hubEvent) {
        DeviceAddedEvent event = (DeviceAddedEvent) hubEvent;

        return HubEventAvro.newBuilder()
            .setHubId(event.getHubId())
            .setTimestamp(event.getTimestamp())
            .setPayload(new DeviceAddedEventAvro(event.getId(), toDeviceTypeAvro(event.getDeviceType())))
            .build();
    }

    private DeviceTypeAvro toDeviceTypeAvro(DeviceType deviceType) {
        return DeviceTypeAvro.valueOf(deviceType.name());
    }
}
