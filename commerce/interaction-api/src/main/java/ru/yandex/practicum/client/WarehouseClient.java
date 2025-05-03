package ru.yandex.practicum.client;

import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {
    @PutMapping
    public void addNewProductInWarehouse(@Valid @RequestBody NewProductInWarehouseRequestDto requestProductDto);

    @PostMapping("/add")
    public void addQuantityProductInWarehouse(@Valid @RequestBody AddProductToWarehouseRequestDto requestQuantityDto);

    @GetMapping("/address")
    public AddressDto getWarehouseAddress();

    @PostMapping("/check")
    BookedProductsDto checkProductsQuantity(@RequestBody ShoppingCartDto shoppingCartDto) throws FeignException;

    @PostMapping("/return")
    void returnProductsToWarehouse(Map<UUID, Long> returnProducts);

    @PostMapping("/shipped")
    void shippedDelivery(ShippedToDeliveryRequestDto shippedToDeliveryRequest);
}
