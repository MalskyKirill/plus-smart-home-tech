package ru.yandex.practicum.handler.sensor;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.model.sensor.SwitchSensorEvent;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class SwitchSensorHandler extends BaseSensorHandler {
    public SwitchSensorHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SpecificRecordBase toSensorEventAvro(SensorEvent sensorEvent) {
        SwitchSensorEvent event = (SwitchSensorEvent) sensorEvent;

        return SensorEventAvro.newBuilder()
            .setId(sensorEvent.getId())
            .setHubId(sensorEvent.getHubId())
            .setTimestamp(sensorEvent.getTimestamp())
            .setPayload(new SwitchSensorAvro(event.getState()))
            .build();
    }
}
