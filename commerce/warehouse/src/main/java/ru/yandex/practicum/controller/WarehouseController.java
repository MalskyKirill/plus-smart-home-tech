package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.NewProductInWarehouseRequestDto;
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

}
