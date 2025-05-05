package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.util.UUID;

@FeignClient(name = "payment", path = "/api/v1/payment")
public interface PaymentClient {

    @PostMapping
    PaymentDto createPayment(OrderDto orderDto);

    @PostMapping("/productCost")
    Double calculateProductCost(OrderDto orderDto);

    @PostMapping("/totalCost")
    Double calculateTotalCost(OrderDto orderDto);

    @PostMapping("/refund")
    void refundPayment(@RequestBody UUID paymentId);

    @PostMapping("/failed")
    void failedPayment(@RequestBody UUID paymentId);
}
