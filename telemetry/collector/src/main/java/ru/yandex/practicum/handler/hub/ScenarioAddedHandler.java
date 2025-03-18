package ru.yandex.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.yandex.practicum.producer.KafkaEventProducer;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScenarioAddedHandler extends BaseHubHandler {
    public ScenarioAddedHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public HubEventAvro toHubEventAvro(HubEventProto hubEvent) {
        ScenarioAddedEventProto event = hubEvent.getScenarioAdded();

        return HubEventAvro.newBuilder()
            .setHubId(hubEvent.getHubId())
            .setTimestamp(mapTimestampToInstant(hubEvent))
            .setPayload(new ScenarioAddedEventAvro(
                event.getName(),
                toScenarioConditionAvro(event.getConditionList()),
                toDeviceActionAvro(event.getActionList())))
            .build();
    }

    private List<ScenarioConditionAvro> toScenarioConditionAvro(List<ScenarioConditionProto> scenarioConditions) {
        return scenarioConditions.stream()
            .map(condition -> ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
                .setValue(condition.getIntValue())
                .build())
            .collect(Collectors.toList());
    }

    private List<DeviceActionAvro> toDeviceActionAvro(List<DeviceActionProto> deviceActions) {
        return deviceActions.stream()
            .map(action -> DeviceActionAvro.newBuilder()
                .setSensorId(action.getSensorId())
                .setType(ActionTypeAvro.valueOf(action.getType().name()))
                .setValue(action.getValue())
                .build())
            .collect(Collectors.toList());
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return null;
    }
}
