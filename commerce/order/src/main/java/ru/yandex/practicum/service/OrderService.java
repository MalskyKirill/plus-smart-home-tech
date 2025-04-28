package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.CreateNewOrderRequestDto;
import ru.yandex.practicum.dto.OrderDto;

public interface OrderService {
    OrderDto create(CreateNewOrderRequestDto requestDto);
}
