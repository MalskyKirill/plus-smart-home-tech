package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.Pageable;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface ShoppingStoreService {
    ProductDto create(ProductDto productDto);

    ProductDto getProduct(UUID productId);

    ProductDto update(ProductDto productDto);

    boolean delete(UUID productId);

    boolean setQuantityState(SetProductQuantityStateRequest request);

    List<ProductDto> getProducts(ProductCategory productCategory, Pageable pageable);
}
