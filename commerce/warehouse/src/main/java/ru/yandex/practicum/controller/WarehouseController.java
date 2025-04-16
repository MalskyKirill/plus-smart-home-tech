package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.AddProductToWarehouseRequestDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequestDto;
import ru.yandex.practicum.dto.WarehouseAddressDto;
import ru.yandex.practicum.service.WarehouseService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PutMapping
    public void addNewProductInWarehouse(@Valid @RequestBody NewProductInWarehouseRequestDto requestProductDto) {
        log.info("PUT-запрос к эндпоинту: '/api/v1/warehouse' на добавление нового товара с id {} на складе", requestProductDto.getProductId());
        warehouseService.addNewProduct(requestProductDto);
    }

    @PostMapping("/add")
    public void addQuantityProductInWarehouse(@Valid @RequestBody AddProductToWarehouseRequestDto requestQuantityDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/warehouse/add' на обновление количества товара с id {} на складе", requestQuantityDto.getProductId());
        warehouseService.addQuantity(requestQuantityDto);
    }

    @GetMapping("/address")
    public WarehouseAddressDto getWarehouseAddress() {
        log.info("POST-запрос к эндпоинту: '/api/v1/warehouse/address' на получение адреса склада для расчёта доставки");
        return warehouseService.getAddress();
    }

}
