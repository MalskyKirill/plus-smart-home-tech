package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.util.UUID;

public interface PaymentService {
    PaymentDto create(OrderDto orderDto);

    Double productCost(OrderDto orderDto);

    Double totalCost(OrderDto orderDto);

    void refund(UUID paymentId);

    void failed(UUID paymentId);
}
