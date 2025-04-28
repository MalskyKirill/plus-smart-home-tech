package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.client.OrderClient;
import ru.yandex.practicum.dto.CreateNewOrderRequestDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.service.OrderService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController implements OrderClient {
    private final OrderService orderService;

    @Override
    public OrderDto createNewOrder(CreateNewOrderRequestDto requestDto) {
        log.info("PUT-запрос к эндпоинту: '/api/v1/order' на создание нового заказа");
        return orderService.create(requestDto);
    }
}
