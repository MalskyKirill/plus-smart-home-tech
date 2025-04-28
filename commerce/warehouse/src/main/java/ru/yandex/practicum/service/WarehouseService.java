package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {
    void addNewProduct(NewProductInWarehouseRequestDto requestProductDto);

    void addQuantity(AddProductToWarehouseRequestDto requestQuantityDto);

    AddressDto getAddress();

    BookedProductsDto checkQuantity(ShoppingCartDto shoppingCartDto);

    void returnProducts(Map<UUID, Long> returnProducts);
}
