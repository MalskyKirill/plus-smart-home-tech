package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class TemperatureSensorHandler extends BaseSensorHandler {
    public TemperatureSensorHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventAvro toSensorEventAvro(SensorEventProto sensorEvent) {
        TemperatureSensorProto event = sensorEvent.getTemperatureSensorEvent();

        return SensorEventAvro.newBuilder()
            .setId(sensorEvent.getId())
            .setHubId(sensorEvent.getHubId())
            .setTimestamp(mapTimestampToInstant(sensorEvent))
            .setPayload(new TemperatureSensorAvro(event.getTemperatureC(), event.getTemperatureF()))
            .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT;
    }
}
