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

    OrderDto paymentFailed(UUID orderId);

    OrderDto completed(UUID orderId);

    OrderDto calculateDelivery(UUID orderId);

    OrderDto calculateTotal(UUID orderId);

    OrderDto delivery(UUID orderId);

    OrderDto assembly(UUID orderId);

    OrderDto deliveryFailed(UUID orderId);

    OrderDto assemblyFailed(UUID orderId);
}
