package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.client.DeliveryClient;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController implements DeliveryClient {
    private final DeliveryService deliveryService;
    @Override
    public DeliveryDto createNewDelivery(DeliveryDto deliveryDto) {
        log.info("PUT-запрос к эндпоинту: '/api/v1/delivery' на создание новой доставки");
        return deliveryService.create(deliveryDto);
    }

    @Override
    public Double calculateDeliveryCost(OrderDto orderDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/delivery/cost' на создание новой доставки");
        return deliveryService.cost(orderDto);
    }
}
