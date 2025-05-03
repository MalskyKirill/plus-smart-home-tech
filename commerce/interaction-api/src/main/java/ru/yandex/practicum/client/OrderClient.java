package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CreateNewOrderRequestDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequestDto;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order", path = "/api/v1/order")
public interface OrderClient {
    @PutMapping
    OrderDto createNewOrder(@RequestBody CreateNewOrderRequestDto requestDto);

    @GetMapping
    List<OrderDto> getOrdersByUser(@RequestParam String username);

    @PostMapping("/return")
    OrderDto returnOrder(@RequestBody ProductReturnRequestDto productReturnRequestDto);

    @PostMapping("/payment")
    OrderDto paymentOrder(@RequestBody UUID orderId);

    @PostMapping("/payment/failed")
    OrderDto paymentOrderFailed(@RequestBody UUID orderId);

    @PostMapping("/completed")
    OrderDto paymentOrderCompleted(@RequestBody UUID orderId);

    @PostMapping("/calculate/delivery")
    OrderDto calculateDeliveryPrice(@RequestBody UUID orderId);

    @PostMapping("/calculate/total")
    OrderDto calculateTotalPrice(@RequestBody UUID orderId);

    @PostMapping("/delivery")
    OrderDto deliveryOrder(@RequestBody UUID orderId);
}
