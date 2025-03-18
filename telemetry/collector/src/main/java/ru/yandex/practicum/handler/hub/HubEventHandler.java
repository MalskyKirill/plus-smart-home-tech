package ru.yandex.practicum.handler.hub;

import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.model.hub.HubEvent;


public interface HubEventHandler {
    HubEventProto.PayloadCase getMessageType();

    void handle(HubEventProto hubEvent);
}
