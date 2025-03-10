package ru.yandex.practicum.handler.sensor;

import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.producer.KafkaEventProducer;

@RequiredArgsConstructor
public abstract class BaseSensorHandler implements SensorEventHandler {
    private final KafkaEventProducer producer;
    private String topic = "telemetry.sensors.v1";

    @Override
    public void handle(SensorEvent sensorEvent) {
        producer.send(toSensorEventAvro(sensorEvent), sensorEvent.getHubId(), sensorEvent.getTimestamp(), topic);
    }

    public abstract SpecificRecordBase toSensorEventAvro(SensorEvent sensorEvent);
}
