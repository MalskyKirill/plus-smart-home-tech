package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCardController {
    private final ShoppingCartService shoppingCartService;

    @PutMapping
    public ShoppingCartDto addProductsInShoppingCart (@RequestParam String username,
                                                      @RequestBody Map<UUID, Long> products) {
        log.info("PUT-запрос к эндпоинту: '/api/v1/shopping-cart' на добавление товара в корзину");
        return shoppingCartService.addProducts(username, products);
    }
}
