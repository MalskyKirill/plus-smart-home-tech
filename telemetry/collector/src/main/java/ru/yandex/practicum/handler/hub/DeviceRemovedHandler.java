package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class DeviceRemovedHandler extends BaseHubHandler {
    public DeviceRemovedHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public HubEventAvro toHubEventAvro(HubEventProto hubEvent) {
        DeviceRemovedEventProto event = hubEvent.getDeviceRemoved();

        return HubEventAvro.newBuilder()
            .setHubId(hubEvent.getHubId())
            .setTimestamp(mapTimestampToInstant(hubEvent))
            .setPayload(new DeviceRemovedEventAvro(event.getId()))
            .build();
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }
}
