package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class ClimateSensorHandler extends BaseSensorHandler{
    public ClimateSensorHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SensorEventAvro toSensorEventAvro(SensorEventProto sensorEvent) {
        ClimateSensorProto event = sensorEvent.getClimateSensorEvent();

        return SensorEventAvro.newBuilder()
            .setId(sensorEvent.getId())
            .setHubId(sensorEvent.getHubId())
            .setTimestamp(mapTimestampToInstant(sensorEvent))
            .setPayload(new ClimateSensorAvro(event.getTemperatureC(), event.getHumidity(), event.getCo2Level()))
            .build();
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        return SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT;
    }
}
