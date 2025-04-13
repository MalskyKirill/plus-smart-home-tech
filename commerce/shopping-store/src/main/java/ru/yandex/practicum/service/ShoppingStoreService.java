package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.ProductDto;

import java.util.UUID;

public interface ShoppingStoreService {
    ProductDto create(ProductDto productDto);

    ProductDto getProduct(UUID productId);
}
