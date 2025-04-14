package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.SetProductQuantityStateRequest;

import java.util.UUID;

public interface ShoppingStoreService {
    ProductDto create(ProductDto productDto);

    ProductDto getProduct(UUID productId);

    ProductDto update(ProductDto productDto);

    boolean delete(UUID productId);

    boolean setQuantityState(SetProductQuantityStateRequest request);
}
