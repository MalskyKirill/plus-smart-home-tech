package ru.yandex.practicum.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.handler.sensor.*;
import ru.yandex.practicum.model.enums.SensorEventType;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SensorEventHandlerConfig {
    private final ClimateSensorHandler climateSensorHandler;
    private final LightSensorHandler lightSensorHandler;
    private final MotionSensorHandler motionSensorHandler;
    private final SwitchSensorHandler switchSensorHandler;
    private final TemperatureSensorHandler temperatureSensorHandler;

    @Bean
    public Map<SensorEventType, SensorEventHandler> getSensorEventsHandlers() {
        return Map.of(
            SensorEventType.CLIMATE_SENSOR_EVENT, climateSensorHandler,
            SensorEventType.LIGHT_SENSOR_EVENT, lightSensorHandler,
            SensorEventType.MOTION_SENSOR_EVENT, motionSensorHandler,
            SensorEventType.SWITCH_SENSOR_EVENT, switchSensorHandler,
            SensorEventType.TEMPERATURE_SENSOR_EVENT, switchSensorHandler
        );
    }
}
