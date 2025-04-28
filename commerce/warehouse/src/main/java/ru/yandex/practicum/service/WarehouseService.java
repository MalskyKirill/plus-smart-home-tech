package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.*;

public interface WarehouseService {
    void addNewProduct(NewProductInWarehouseRequestDto requestProductDto);

    void addQuantity(AddProductToWarehouseRequestDto requestQuantityDto);

    AddressDto getAddress();

    BookedProductsDto checkQuantity(ShoppingCartDto shoppingCartDto);
}
