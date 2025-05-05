package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.util.UUID;

public interface DeliveryService {
    DeliveryDto create(DeliveryDto deliveryDto);

    Double cost(OrderDto orderDto);

    void picked(UUID deliveryId);

    void successful(UUID deliveryId);

    void failed(UUID deliveryId);
}
