package ru.yandex.practicum.handler.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.model.hub.DeviceAction;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.ScenarioAddedEvent;
import ru.yandex.practicum.model.hub.ScenarioCondition;
import ru.yandex.practicum.producer.KafkaEventProducer;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScenarioAddedHandler extends BaseHubHandler{
    public ScenarioAddedHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public SpecificRecordBase toHubEventAvro(HubEvent hubEvent) {
        ScenarioAddedEvent event = (ScenarioAddedEvent) hubEvent;

        return HubEventAvro.newBuilder()
            .setHubId(hubEvent.getHubId())
            .setTimestamp(hubEvent.getTimestamp())
            .setPayload(new ScenarioAddedEventAvro(
                event.getName(),
                toScenarioConditionAvro(event.getConditions()),
                toDeviceActionAvro(event.getActions())))
            .build();
    }

    private List<ScenarioConditionAvro> toScenarioConditionAvro(List<ScenarioCondition> scenarioConditions) {
        return scenarioConditions.stream()
            .map(condition -> ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
                .setValue(condition.getValue())
                .build())
            .collect(Collectors.toList());
    }

    private List<DeviceActionAvro> toDeviceActionAvro(List<DeviceAction> deviceActions) {
        return deviceActions.stream()
            .map(action -> DeviceActionAvro.newBuilder()
                .setSensorId(action.getSensorId())
                .setType(ActionTypeAvro.valueOf(action.getType().name()))
                .setValue(action.getValue())
                .build())
            .collect(Collectors.toList());
    }
}
