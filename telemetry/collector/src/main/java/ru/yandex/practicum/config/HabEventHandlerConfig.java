package ru.yandex.practicum.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.handler.hub.*;
import ru.yandex.practicum.model.enums.HubEventType;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class HabEventHandlerConfig {
    private final DeviceAddedHandler deviceAddedHandler;
    private final DeviceRemovedHandler deviceRemovedHandler;
    private final ScenarioAddedHandler scenarioAddedHandler;
    private final ScenarioRemovedHandler scenarioRemovedHandler;

    @Bean
    public Map<HubEventType, HubEventHandler> getHubEventsHandlers() {
        return Map.of(
            HubEventType.DEVICE_ADDED, deviceAddedHandler,
            HubEventType.DEVICE_REMOVED, deviceRemovedHandler,
            HubEventType.SCENARIO_ADDED, scenarioAddedHandler,
            HubEventType.SCENARIO_REMOVED, scenarioRemovedHandler);
    }
}
