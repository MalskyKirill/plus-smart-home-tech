package ru.yandex.practicum.service;

import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

public interface ShoppingCartService {
    ShoppingCartDto addProducts(String username, Map<UUID, Long> products);

    ShoppingCartDto getShoppingCart(String username);
}
