package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class MotionSensorHandler extends BaseSensorHandler {
    public MotionSensorHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventAvro toSensorEventAvro(SensorEventProto sensorEvent) {
        MotionSensorProto event = sensorEvent.getMotionSensorEvent();

        return SensorEventAvro.newBuilder()
            .setId(sensorEvent.getId())
            .setHubId(sensorEvent.getHubId())
            .setTimestamp(mapTimestampToInstant(sensorEvent))
            .setPayload(new MotionSensorAvro(event.getLinkQuality(), event.getMotion(), event.getVoltage()))
            .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT;
    }
}
