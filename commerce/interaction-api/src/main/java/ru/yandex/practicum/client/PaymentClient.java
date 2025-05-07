package ru.yandex.practicum.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.util.UUID;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentClient {

    @PostMapping
    PaymentDto createPayment(OrderDto orderDto) throws FeignException;

    @PostMapping("/productCost")
    Double calculateProductCost(OrderDto orderDto) throws FeignException;

    @PostMapping("/totalCost")
    Double calculateTotalCost(OrderDto orderDto) throws FeignException;

    @PostMapping("/refund")
    void refundPayment(@RequestBody UUID paymentId) throws FeignException;

    @PostMapping("/failed")
    void failedPayment(@RequestBody UUID paymentId) throws FeignException;
}
