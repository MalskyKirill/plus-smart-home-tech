package ru.yandex.practicum.handler.hub;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.ScenarioRemovedEvent;
import ru.yandex.practicum.producer.KafkaEventProducer;

@Component
public class ScenarioRemovedHandler extends BaseHubHandler{

    public ScenarioRemovedHandler(KafkaEventProducer producer) {
        super(producer);
    }

    @Override
    public HubEventAvro toHubEventAvro(HubEventProto hubEvent) {
        ScenarioRemovedEventProto event = hubEvent.getScenarioRemoved();

        return HubEventAvro.newBuilder()
            .setHubId(hubEvent.getHubId())
            .setTimestamp(mapTimestampToInstant(hubEvent))
            .setPayload(new ScenarioRemovedEventAvro(event.getName()))
            .build();
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }
}
