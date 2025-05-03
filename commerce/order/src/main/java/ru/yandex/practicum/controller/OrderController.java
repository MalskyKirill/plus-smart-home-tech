package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.client.OrderClient;
import ru.yandex.practicum.dto.CreateNewOrderRequestDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequestDto;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

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

    @Override
    public List<OrderDto> getOrdersByUser(String username) {
        log.info("GET-запрос к эндпоинту: '/api/v1/order' на получение заказов пользователя");
        return orderService.get(username);
    }

    @Override
    public OrderDto returnOrder(ProductReturnRequestDto productReturnRequestDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/order/return' на возврат заказа");
        return orderService.returnOrder(productReturnRequestDto);
    }

    @Override
    public OrderDto paymentProduct(UUID orderId) {
        log.info("POST-запрос к эндпоинту: '/api/v1/order/payment' на оплату товара");
        return orderService.payment(orderId);
    }

    @Override
    public OrderDto paymentProductFailed(UUID orderId) {
        log.info("POST-запрос к эндпоинту: '/api/v1/order/payment/failed' на изменение статуса заказа в случае ошибки");
        return orderService.paymentFailed(orderId);
    }

    @Override
    public OrderDto paymentProductCompleted(UUID orderId) {
        log.info("POST-запрос к эндпоинту: '/api/v1/order/completed' на изменение статуса заказа при его завершении");
        return orderService.completed(orderId);
    }

    @Override
    public OrderDto calculateDeliveryPrice(UUID orderId) {
        log.info("POST-запрос к эндпоинту: '/api/v1/calculate/delivery' на получение стоимости доставки");
        return orderService.calculateDelivery(orderId);
    }

    @Override
    public OrderDto calculateTotalPrice(UUID orderId) {
        log.info("POST-запрос к эндпоинту: '/api/v1/calculate/total' на расчет стоимости заказа");
        return orderService.calculateTotal(orderId);
    }


}
