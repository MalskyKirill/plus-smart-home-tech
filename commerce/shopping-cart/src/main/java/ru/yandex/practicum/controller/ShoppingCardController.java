package ru.yandex.practicum.controller;

import client.ShoppingCartClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ChangeProductQuantityRequestDto;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.service.ShoppingCartService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-cart")
public class ShoppingCardController implements ShoppingCartClient {
    private final ShoppingCartService shoppingCartService;

    @Override
    public ShoppingCartDto addProductsInShoppingCart (@RequestParam String username,
                                                      @RequestBody Map<UUID, Long> products) {
        log.info("PUT-запрос к эндпоинту: '/api/v1/shopping-cart' на добавление товара в корзину");
        return shoppingCartService.addProducts(username, products);
    }

    @Override
    public ShoppingCartDto getActualShoppingCartByUser (@RequestParam String username) {
        log.info("GET-запрос к эндпоинту: '/api/v1/shopping-cart' на получение актуальной корзину для авторизованного пользователя");
        return shoppingCartService.getShoppingCart(username);
    }

    @Override
    public void deactivateShoppingCartByUser (@RequestParam String username) {
        log.info("DELETE-запрос к эндпоинту: '/api/v1/shopping-cart' на деактивацию корзины товаров для пользователя");
        shoppingCartService.deactivateShoppingCart(username);
    }

    @Override
    public ShoppingCartDto removeProductFromShoppingCart (@RequestParam String username,
                                                          @RequestBody List<UUID> products) {
        log.info("POST-запрос к эндпоинту: '/api/v1/shopping-cart/remove' на удаление товаров из корзины");
        return shoppingCartService.remove(username, products);
    }

    @Override
    public ShoppingCartDto changeProductQuantityInShoppingCart (@RequestParam String username,
                                                                @RequestBody ChangeProductQuantityRequestDto requestDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/shopping-cart/change-quantity' на изменение товаров в корзины");
        return shoppingCartService.changeQuantity(username, requestDto);
    }
}
