package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.client.PaymentClient;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController implements PaymentClient {
    private final PaymentService paymentService;

    @Override
    public PaymentDto createPayment(OrderDto orderDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/payment' на создание нового платежа");
        return paymentService.create(orderDto);
    }

    @Override
    public Double calculateProductCost(OrderDto orderDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/payment/productCost' на расчет стоимости товаров в заказе");
        return paymentService.productCost(orderDto);
    }

    @Override
    public Double calculateTotalCost(OrderDto orderDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/payment/totalCost' на расчет стоимости общей стоимости товара");
        return paymentService.totalCost(orderDto);
    }

    @Override
    public void refundPayment(UUID paymentId) {
        log.info("POST-запрос к эндпоинту: '/api/v1/payment/refund' для эмуляции успешной оплаты в платежного шлюза");
        paymentService.refund(paymentId);
    }

    @Override
    public void failedPayment(UUID paymentId) {
        log.info("POST-запрос к эндпоинту: '/api/v1/payment/failed' для эмуляции отказа в оплате платежного шлюза");
        paymentService.failed(paymentId);
    }
}
