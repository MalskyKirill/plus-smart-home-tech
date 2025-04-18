package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.*;

public interface WarehouseService {
    void addNewProduct(NewProductInWarehouseRequestDto requestProductDto);

    void addQuantity(AddProductToWarehouseRequestDto requestQuantityDto);

    WarehouseAddressDto getAddress();

    BookedProductsDto checkQuantity(ShoppingCartDto shoppingCartDto);
}
