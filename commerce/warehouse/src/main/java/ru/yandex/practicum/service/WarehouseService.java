package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.NewProductInWarehouseRequestDto;

public interface WarehouseService {
    void addNewProduct(NewProductInWarehouseRequestDto requestProductDto);
}
