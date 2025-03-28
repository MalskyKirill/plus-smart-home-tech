package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Enum.ActionType;
import ru.yandex.practicum.model.Enum.ConditionOperation;
import ru.yandex.practicum.model.Enum.ConditionType;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.repository.ActionRepository;
import ru.yandex.practicum.repository.ConditionRepository;
import ru.yandex.practicum.repository.ScenarioRepository;
import ru.yandex.practicum.repository.SensorRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioAddedEventHandler implements HubEventHandler{
    private final ScenarioRepository scenarioRepository;
    private final SensorRepository sensorRepository;
    private final ActionRepository actionRepository;
    private final ConditionRepository conditionRepository;

    @Override
    public String getType() {
        return ScenarioAddedEventAvro.class.getSimpleName();
    }

    @Override
    @Transactional
    public void handle(HubEventAvro hubEventAvro) {
        ScenarioAddedEventAvro scenarioAddedEventAvro = (ScenarioAddedEventAvro) hubEventAvro.getPayload();

        Optional<Scenario> scenarioOpt = scenarioRepository.findByHubIdAndName(hubEventAvro.getHubId(), scenarioAddedEventAvro.getName());

        if(scenarioOpt.isEmpty()) {
            Scenario scenario = scenarioRepository.save(toScenario(hubEventAvro));
            log.info("сохраняем новый сценарий {} = ", scenario);
            if (checkSensors(getConditionsSensorIds(scenarioAddedEventAvro.getConditions()), hubEventAvro.getHubId())) {
                conditionRepository.saveAll(toCondition(scenarioAddedEventAvro, scenario));
            } else {
                throw new RuntimeException("Не найдены сенсоры условий сценария");
            }

            if (checkSensors(getActionsSensorIds(scenarioAddedEventAvro.getActions()), hubEventAvro.getHubId())) {
                actionRepository.saveAll(toAction(scenarioAddedEventAvro, scenario));
            } else {
                throw new RuntimeException("Не найдены сенсоры действий сценария");
            }
        } else {
            Scenario scenario = scenarioOpt.get();
            log.info("достали сценарий {} = ", scenario);
            if (checkSensors(getConditionsSensorIds(scenarioAddedEventAvro.getConditions()), hubEventAvro.getHubId())) {
                conditionRepository.saveAll(toCondition(scenarioAddedEventAvro, scenario));
            } else {
                throw new RuntimeException("Не найдены сенсоры условий сценария");
            }

            if (checkSensors(getActionsSensorIds(scenarioAddedEventAvro.getActions()), hubEventAvro.getHubId())) {
                actionRepository.saveAll(toAction(scenarioAddedEventAvro, scenario));
            } else {
                throw new RuntimeException("Не найдены сенсоры действий сценария");
            }
        }

    }

    private Set<Condition> toCondition(ScenarioAddedEventAvro scenarioAddedEventAvro, Scenario scenario) {
        return scenarioAddedEventAvro
            .getConditions()
            .stream()
            .map(condition -> Condition
                .builder()
                .sensor(sensorRepository.findById(condition.getSensorId()).orElseThrow())
                .scenario(scenario)
                .type(toConditionType(condition.getType()))
                .operation(toConditionOperation(condition.getOperation()))
                .value(getConditionValue(condition.getValue()))
                .build())
            .collect(Collectors.toSet());
    }

    private Set<Action> toAction(ScenarioAddedEventAvro scenarioAddedEventAvro, Scenario scenario) {
        return scenarioAddedEventAvro
            .getActions()
            .stream()
            .map(action -> Action
                .builder()
                .sensor(sensorRepository.findById(action.getSensorId()).orElseThrow())
                .scenario(scenario)
                .type(toActionType(action.getType()))
                .value(action.getValue())
                .build())
            .collect(Collectors.toSet());
    }

    private ConditionType toConditionType(ConditionTypeAvro conditionTypeAvro) {
        return ConditionType.valueOf(conditionTypeAvro.name());
    }

    private ConditionOperation toConditionOperation(ConditionOperationAvro conditionOperationAvro) {
        return ConditionOperation.valueOf(conditionOperationAvro.name());
    }

    private ActionType toActionType(ActionTypeAvro actionTypeAvro) {
        return ActionType.valueOf(actionTypeAvro.name());
    }

    private Integer getConditionValue(Object conditionValue) {
        if (conditionValue == null) {
            return null;
        }
        if (conditionValue instanceof Boolean) {
            return ((Boolean) conditionValue ? 1 : 0);
        }
        if (conditionValue instanceof Integer) {
            return (Integer) conditionValue;
        }
        throw new ClassCastException("Ошибка преобразования значения");
    }

    private Scenario toScenario(HubEventAvro hubEventAvro) {
        ScenarioAddedEventAvro scenarioAddedEventAvro = (ScenarioAddedEventAvro) hubEventAvro.getPayload();

        return Scenario.builder().name(scenarioAddedEventAvro.getName()).hubId(hubEventAvro.getHubId()).build();
    }

    private List<String> getConditionsSensorIds(Collection<ScenarioConditionAvro> conditionsAvro) {
        return conditionsAvro.stream().map(ScenarioConditionAvro::getSensorId).collect(Collectors.toList());
    }

    private List<String> getActionsSensorIds(Collection<DeviceActionAvro> actionsAvro) {
        return actionsAvro.stream().map(DeviceActionAvro::getSensorId).collect(Collectors.toList());
    }

    private boolean checkSensors(Collection<String> ids, String hubId) {
        return sensorRepository.existsByIdInAndHubId(ids, hubId);
    }
}
