package ru.yandex.practicum.handler.sensor;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.producer.KafkaEventProducer;

import java.time.Instant;

@RequiredArgsConstructor
public abstract class BaseSensorHandler implements SensorEventHandler {
    private final KafkaEventProducer producer;
    private String topic = "telemetry.sensors.v1";

    @Override
    public void handle(SensorEventProto sensorEvent) {
        producer.send(toSensorEventAvro(sensorEvent), sensorEvent.getHubId(), mapTimestampToInstant(sensorEvent), topic);
    }

    public Instant mapTimestampToInstant(SensorEventProto hubEvent) {
        return Instant.ofEpochSecond(hubEvent.getTimestamp().getSeconds(), hubEvent.getTimestamp().getNanos());
    }

    public abstract SensorEventAvro toSensorEventAvro(SensorEventProto sensorEvent);
}
