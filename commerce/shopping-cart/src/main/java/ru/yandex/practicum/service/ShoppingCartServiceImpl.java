package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.dto.enums.ShoppingCartState;
import ru.yandex.practicum.exeption.MissingUsernameException;
import ru.yandex.practicum.mapper.ShoppingCartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.ShoppingCartRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional
    public ShoppingCartDto addProducts(String username, Map<UUID, Long> products) {
        checkUserName(username);

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserName(username).orElse(getNewShoppingCart(username));

        Map<UUID, Long> mapProducts = new HashMap<>();
        if (shoppingCart.getProducts() != null && !shoppingCart.getProducts().isEmpty()) {
            mapProducts.putAll(shoppingCart.getProducts());
        }

        mapProducts.putAll(products);
        shoppingCart.setProducts(mapProducts);
        return ShoppingCartMapper.toShoppingCartDto(shoppingCartRepository.save(shoppingCart));
    }

    private ShoppingCart getNewShoppingCart(String username) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserName(username);
        shoppingCart.setState(ShoppingCartState.ACTIVE);
        return shoppingCart;
    }

    private void checkUserName(String username) {
        if(username.isBlank()) {
            throw new MissingUsernameException("отсутствует имя пользователя");
        }
    }
}
