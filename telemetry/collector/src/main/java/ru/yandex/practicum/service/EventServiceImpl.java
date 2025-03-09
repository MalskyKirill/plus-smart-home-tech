package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.handler.hub.HubEventHandler;
import ru.yandex.practicum.model.enums.HubEventType;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{
    private final Map<HubEventType, HubEventHandler> hubEventHandlers;

    @Override
    public void collectHubEvent(HubEvent hubEvent) {
        hubEventHandlers.get(hubEvent.getType()).handle(hubEvent);
    }

    @Override
    public void collectSensorEvent(SensorEvent sensorEvent) {

    }
}
