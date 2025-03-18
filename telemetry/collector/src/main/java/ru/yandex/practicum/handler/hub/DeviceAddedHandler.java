package ru.yandex.practicum.handler.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class DeviceAddedHandler extends BaseHubHandler{
    public DeviceAddedHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public HubEventAvro toHubEventAvro(HubEventProto hubEvent) {
        DeviceAddedEventProto event = hubEvent.getDeviceAdded();

        return HubEventAvro.newBuilder()
            .setHubId(hubEvent.getHubId())
            .setTimestamp(mapTimestampToInstant(hubEvent))
            .setPayload(new DeviceAddedEventAvro(event.getId(), toDeviceTypeAvro(event.getType())))
            .build();
    }

    private DeviceTypeAvro toDeviceTypeAvro(DeviceTypeProto deviceType) {
        return DeviceTypeAvro.valueOf(deviceType.name());
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }
}
