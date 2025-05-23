package ru.yandex.practicum.mapper;


import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.model.ShoppingCart;

public class ShoppingCartMapper {
    public static ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart) {
        return ShoppingCartDto.builder()
            .cartId(shoppingCart.getCardId())
            .products(shoppingCart.getProducts())
            .build();
    }
}
