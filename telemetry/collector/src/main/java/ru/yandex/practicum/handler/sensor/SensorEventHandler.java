package ru.yandex.practicum.handler.sensor;

import ru.yandex.practicum.model.sensor.SensorEvent;

public interface SensorEventHandler {
    void handle(SensorEvent sensorEvent);
}
