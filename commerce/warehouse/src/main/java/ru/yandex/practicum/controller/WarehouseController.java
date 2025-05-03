package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.service.WarehouseService;

import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController implements WarehouseClient {
    private final WarehouseService warehouseService;

    @Override
    public void addNewProductInWarehouse(@Valid @RequestBody NewProductInWarehouseRequestDto requestProductDto) {
        log.info("PUT-запрос к эндпоинту: '/api/v1/warehouse' на добавление нового товара с id {} на складе", requestProductDto.getProductId());
        warehouseService.addNewProduct(requestProductDto);
    }

    @Override
    public void addQuantityProductInWarehouse(@Valid @RequestBody AddProductToWarehouseRequestDto requestQuantityDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/warehouse/add' на обновление количества товара с id {} на складе", requestQuantityDto.getProductId());
        warehouseService.addQuantity(requestQuantityDto);
    }

    @Override
    public AddressDto getWarehouseAddress() {
        log.info("POST-запрос к эндпоинту: '/api/v1/warehouse/address' на получение адреса склада для расчёта доставки");
        return warehouseService.getAddress();
    }

    @Override
    public BookedProductsDto checkProductsQuantity(ShoppingCartDto shoppingCartDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/warehouse/check' для проверки количества товара на складе");
        return warehouseService.checkQuantity(shoppingCartDto);
    }

    @Override
    public void returnProductsToWarehouse(Map<UUID, Long> returnProducts) {
        log.info("POST-запрос к эндпоинту: '/api/v1/warehouse/return' для возврата товара на склад");
        warehouseService.returnProducts(returnProducts);
    }

    @Override
    public void shippedDelivery(ShippedToDeliveryRequestDto shippedToDeliveryRequest) {
        log.info("POST-запрос к эндпоинту: '/api/v1/warehouse/shipped' для передачи товаров в доставку");
        warehouseService.shipped(shippedToDeliveryRequest);
    }

}
