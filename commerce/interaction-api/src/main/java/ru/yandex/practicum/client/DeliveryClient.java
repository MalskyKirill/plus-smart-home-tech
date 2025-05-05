package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.util.UUID;

@FeignClient(name = "delivery", path = "/api/v1/delivery")
public interface DeliveryClient {
    @PutMapping
    DeliveryDto createNewDelivery(@RequestBody DeliveryDto deliveryDto);

    @PostMapping("/cost")
    Double calculateDeliveryCost(OrderDto orderDto);

    @PostMapping("/picked")
    void pickedOrder(UUID deliveryId);

    @PostMapping("/successful")
    void successfulDelivery(@RequestBody UUID deliveryId);

    @PostMapping("/failed")
    void failedDelivery(@RequestBody UUID deliveryId);
}
