package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CreateNewOrderRequestDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequestDto;

import java.util.List;

@FeignClient(name = "order", path = "/api/v1/order")
public interface OrderClient {
    @PutMapping
    OrderDto createNewOrder(@RequestBody CreateNewOrderRequestDto requestDto);

    @GetMapping
    List<OrderDto> getOrdersByUser(@RequestParam String username);

    @PostMapping("/return")
    OrderDto returnProduct(@RequestBody ProductReturnRequestDto productReturnRequestDto);
}
