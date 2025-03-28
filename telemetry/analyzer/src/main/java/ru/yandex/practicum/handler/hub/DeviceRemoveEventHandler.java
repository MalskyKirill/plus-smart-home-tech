package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.repository.SensorRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeviceRemoveEventHandler implements HubEventHandler {
    private final SensorRepository sensorRepository;
    @Override
    public String getType() {
        return DeviceRemovedEventAvro.class.getSimpleName();
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        String sensorId = ((DeviceRemovedEventAvro) hubEventAvro.getPayload()).getId();
        log.info("удаляем девайс с id = {}, у хаба с id = {}", sensorId, hubEventAvro.getHubId());
        sensorRepository.deleteByIdAndHubId(sensorId, hubEventAvro.getHubId());
    }
}
