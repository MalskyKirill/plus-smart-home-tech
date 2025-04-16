package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.AddProductToWarehouseRequestDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequestDto;
import ru.yandex.practicum.dto.WarehouseAddressDto;

public interface WarehouseService {
    void addNewProduct(NewProductInWarehouseRequestDto requestProductDto);

    void addQuantity(AddProductToWarehouseRequestDto requestQuantityDto);

    WarehouseAddressDto getAddress();
}
