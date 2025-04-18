package ru.yandex.practicum.handler.snapshot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.handler.sensor.SensorEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.model.Condition;
import ru.yandex.practicum.model.Enum.ConditionOperation;
import ru.yandex.practicum.model.Scenario;
import ru.yandex.practicum.producer.ScenarioActionProducer;
import ru.yandex.practicum.repository.ScenarioRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SnapshotHandler {
    private final ScenarioRepository scenarioRepository;
    private final ScenarioActionProducer scenarioActionProducer;
    private final Map<String, SensorEventHandler> sensorEventHandlers;

    public SnapshotHandler(ScenarioRepository scenarioRepository,
                               Set<SensorEventHandler> sensorEventHandlers, ScenarioActionProducer scenarioActionProducer) {
        this.scenarioRepository = scenarioRepository;
        this.scenarioActionProducer = scenarioActionProducer;
        this.sensorEventHandlers = sensorEventHandlers.stream()
            .collect(Collectors.toMap(
                SensorEventHandler::getType,
                Function.identity()
            ));
    }

    public void handleSnapshot(SensorsSnapshotAvro sensorsSnapshotAvro) {
        List<Scenario> scenarios = getScenariosBySnapshots(sensorsSnapshotAvro);
        log.info("найдены сценарии для выполнения {}", scenarios.size());
        for (Scenario scenario : scenarios) {
            scenarioActionProducer.sendAction(scenario);
        }
    }

    private List<Scenario> getScenariosBySnapshots(SensorsSnapshotAvro sensorsSnapshotAvro) {
        log.info("хаб {}",sensorsSnapshotAvro.getHubId());
        List<Scenario> scenarios = scenarioRepository.findByHubId(sensorsSnapshotAvro.getHubId());
        Map<String, SensorStateAvro> sensorStates = sensorsSnapshotAvro.getSensorsState();
        log.info("состояния {}", sensorStates.toString());
        log.info("количество сценариев {} ", scenarios.size());

        return scenarios.stream()
            .filter(scenario -> checkConditions(scenario.getConditions(), sensorStates))
            .toList();
    }

    private boolean checkConditions(List<Condition> conditions, Map<String, SensorStateAvro> sensorStates) {
        log.info("условий {}", conditions.size());

        return conditions.stream().allMatch(condition -> {
            try {
                log.info("id {}", condition.getSensor().getId());
                log.info("avro {}", sensorStates.get(condition.getSensor().getId()));
                return checkCondition(condition, sensorStates.get(condition.getSensor().getId()));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return false;
        });
    }

    private boolean checkCondition(Condition condition, SensorStateAvro sensorStateAvro) {
        String type = sensorStateAvro.getData().getClass().getTypeName();
        if (!sensorEventHandlers.containsKey(type)) {
            throw new IllegalArgumentException("нет обработчика для сенсора " + type);
        }

        Integer value = sensorEventHandlers.get(type).getSensorValue(condition.getType(), sensorStateAvro);
        log.info("проверить условие {} для состояния датчика {}", condition, sensorStateAvro);

        if(value == null) {
            return false;
        }

        log.info("значение условия = {}, значение датчика = {}", condition.getValue(), value);
        return switch (condition.getOperation()) {
            case ConditionOperation.LOWER_THAN -> value < condition.getValue();
            case ConditionOperation.EQUALS -> value.equals(condition.getValue());
            case ConditionOperation.GREATER_THAN -> value > condition.getValue();
        };
    }

}

