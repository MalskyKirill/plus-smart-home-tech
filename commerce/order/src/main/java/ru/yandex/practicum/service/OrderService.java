package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.CreateNewOrderRequestDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequestDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto create(CreateNewOrderRequestDto requestDto);

    List<OrderDto> get(String username);

    OrderDto returnOrder(ProductReturnRequestDto productReturnRequestDto);

    OrderDto payment(UUID orderId);
}
