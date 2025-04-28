package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.CreateNewOrderRequestDto;
import ru.yandex.practicum.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto create(CreateNewOrderRequestDto requestDto);

    List<OrderDto> get(String username);
}
