package ru.yandex.practicum.client;

import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.Pageable;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "shopping-store")
public interface ShoppingStoreClient {
    @PutMapping
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) throws FeignException;

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) throws FeignException;

    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto productDto) throws FeignException;

    @PostMapping("/removeProductFromStore")
    public boolean deleteProduct(@RequestBody UUID productId) throws FeignException;

    @PostMapping("/quantityState")
    public boolean setProductQuantityState(@Valid @RequestBody SetProductQuantityStateRequest request) throws FeignException;

    @GetMapping
    public List<ProductDto> getProductsByCategory(@RequestParam ProductCategory category, Pageable pageable) throws FeignException;
}
