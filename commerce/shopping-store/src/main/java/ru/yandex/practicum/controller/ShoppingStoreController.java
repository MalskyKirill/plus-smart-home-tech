package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.service.ShoppingStoreService;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-store")
public class ShoppingStoreController {
    private final ShoppingStoreService shoppingStoreService;

    @PutMapping
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info("PUT-запрос к эндпоинту: '/api/v1/shopping-store' на создание товара c productName = {}", productDto.getProductName());
        return shoppingStoreService.create(productDto);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) {
        log.info("GET-запрос к эндпоинту: '/api/v1/shopping-store/productId' на получение товара c productId = {}", productId);
        return shoppingStoreService.getProduct(productId);
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        log.info("POST-запрос к эндпоинту: '/api/v1/shopping-store' на обновление товара c productName = {}", productDto.getProductName());
        return shoppingStoreService.update(productDto);
    }

}
