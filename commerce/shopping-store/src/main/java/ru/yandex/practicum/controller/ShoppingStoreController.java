package ru.yandex.practicum.controller;

import client.ShoppingStoreClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.Pageable;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.service.ShoppingStoreService;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-store")
public class ShoppingStoreController implements ShoppingStoreClient {
    private final ShoppingStoreService shoppingStoreService;

    @Override
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("PUT-запрос к эндпоинту: '/api/v1/shopping-store' на создание товара c productName = {}", productDto.getProductName());
        return shoppingStoreService.create(productDto);
    }

    @Override
    public ProductDto getProductById(@PathVariable UUID productId) {
        log.info("GET-запрос к эндпоинту: '/api/v1/shopping-store/productId' на получение товара c productId = {}", productId);
        return shoppingStoreService.getProduct(productId);
    }

    @Override
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/shopping-store' на обновление товара c productName = {}", productDto.getProductName());
        return shoppingStoreService.update(productDto);
    }

    @Override
    public boolean deleteProduct(@RequestBody UUID productId) {
        log.info("POST-запрос к эндпоинту: '/api/v1/shopping-store/removeProductFromStore' на удаление товара c productId = {}", productId);
        return shoppingStoreService.delete(productId);
    }

    @Override
    public boolean setProductQuantityState(@Valid @RequestBody SetProductQuantityStateRequest request) {
        log.info("POST-запрос к эндпоинту: '/api/v1/shopping-store/quantityState' на изменение количества товара c productId = {}", request.getProductId());
        return shoppingStoreService.setQuantityState(request);
    }

    @Override
    public List<ProductDto> getProductsByCategory(@RequestParam ProductCategory category, Pageable pageable) {
        log.info("GET-запрос к эндпоинту: '/api/v1/shopping-store' на получение списка товаров по типу {} в пагинированном виде", category);
        return shoppingStoreService.getProducts(category, pageable);
    }
}
