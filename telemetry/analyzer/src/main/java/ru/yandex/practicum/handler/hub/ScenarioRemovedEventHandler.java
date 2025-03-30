package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.repository.ActionRepository;
import ru.yandex.practicum.repository.ConditionRepository;
import ru.yandex.practicum.repository.ScenarioRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler implements HubEventHandler {
    private final ScenarioRepository scenarioRepository;

    @Override
    public String getType() {
        return ScenarioRemovedEventAvro.class.getSimpleName();
    }


    @Transactional
    @Override
    public void handle(HubEventAvro hubEventAvro) {
        String name = ((ScenarioRemovedEventAvro) hubEventAvro.getPayload()).getName();
        log.info("удаляем сценарий с name = {}, у хаба с id = {}", name, hubEventAvro.getHubId());
        scenarioRepository.deleteByHubIdAndName(hubEventAvro.getHubId(),name);
    }
}
