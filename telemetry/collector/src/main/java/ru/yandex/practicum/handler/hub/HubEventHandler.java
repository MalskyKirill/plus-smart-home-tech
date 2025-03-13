package ru.yandex.practicum.handler.hub;

import ru.yandex.practicum.model.hub.HubEvent;

public interface HubEventHandler {
    void handle(HubEvent hubEvent);
}
