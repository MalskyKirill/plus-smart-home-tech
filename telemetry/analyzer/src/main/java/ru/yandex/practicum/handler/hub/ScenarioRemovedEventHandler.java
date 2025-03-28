package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.repository.ActionRepository;
import ru.yandex.practicum.repository.ConditionRepository;
import ru.yandex.practicum.repository.ScenarioRepository;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler implements HubEventHandler {
    private final ScenarioRepository scenarioRepository;
    private final ConditionRepository conditionRepository;
    private final ActionRepository actionRepository;


    @Override
    public String getType() {
        return ScenarioRemovedEventAvro.class.getSimpleName();
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        ScenarioRemovedEventAvro scenarioRemovedEventAvro = (ScenarioRemovedEventAvro) hubEventAvro.getPayload();
        log.info("удаляем сценарий с name = {}, у хаба с id = {}", scenarioRemovedEventAvro.getName(), hubEventAvro.getHubId());
        Optional<Scenario> optScenario = scenarioRepository.findByHubIdAndName(hubEventAvro.getHubId(), scenarioRemovedEventAvro.getName());
        if(optScenario.isEmpty()) {
            log.info("сценарий не найден");
        } else {
            Scenario scenario = optScenario.get();
            conditionRepository.deleteByScenario(scenario);
            actionRepository.deleteByScenario(scenario);
            scenarioRepository.delete(scenario);
        }

    }
}
