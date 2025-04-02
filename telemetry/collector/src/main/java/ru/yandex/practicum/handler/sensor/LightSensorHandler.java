package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class LightSensorHandler extends BaseSensorHandler {
    public LightSensorHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventAvro toSensorEventAvro(SensorEventProto sensorEvent) {
        LightSensorProto event = sensorEvent.getLightSensorEvent();

        return SensorEventAvro.newBuilder()
            .setId(sensorEvent.getId())
            .setHubId(sensorEvent.getHubId())
            .setTimestamp(mapTimestampToInstant(sensorEvent))
            .setPayload(new LightSensorAvro(event.getLinkQuality(), event.getLuminosity()))
            .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT;
    }
}
