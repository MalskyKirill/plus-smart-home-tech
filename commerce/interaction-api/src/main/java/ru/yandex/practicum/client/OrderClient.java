package ru.yandex.practicum.client;

import feign.FeignException;
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
    OrderDto createNewOrder(@RequestBody CreateNewOrderRequestDto requestDto) throws FeignException;

    @GetMapping
    List<OrderDto> getOrdersByUser(@RequestParam String username) throws FeignException;

    @PostMapping("/return")
    OrderDto returnOrder(@RequestBody ProductReturnRequestDto productReturnRequestDto) throws FeignException;

    @PostMapping("/payment")
    OrderDto paymentOrder(@RequestBody UUID orderId) throws FeignException;

    @PostMapping("/payment/failed")
    OrderDto paymentOrderFailed(@RequestBody UUID orderId) throws FeignException;

    @PostMapping("/completed")
    OrderDto paymentOrderCompleted(@RequestBody UUID orderId) throws FeignException;

    @PostMapping("/calculate/delivery")
    OrderDto calculateDeliveryPrice(@RequestBody UUID orderId) throws FeignException;

    @PostMapping("/calculate/total")
    OrderDto calculateTotalPrice(@RequestBody UUID orderId) throws FeignException;

    @PostMapping("/delivery")
    OrderDto deliveryOrder(@RequestBody UUID orderId) throws FeignException;

    @PostMapping("/assembly")
    OrderDto assemblyOrder(@RequestBody UUID orderId) throws FeignException;

    @PostMapping("/delivery/failed")
    OrderDto deliveryFailed(@RequestBody UUID orderId) throws FeignException;

    @PostMapping("/assembly/failed")
    OrderDto assemblyFailed(@RequestBody UUID orderId) throws FeignException;
}
