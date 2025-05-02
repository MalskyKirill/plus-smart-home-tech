package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

public interface DeliveryService {
    DeliveryDto create(DeliveryDto deliveryDto);

    Double cost(OrderDto orderDto);
}
