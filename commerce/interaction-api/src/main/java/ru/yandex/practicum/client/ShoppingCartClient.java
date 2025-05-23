package ru.yandex.practicum.client;

import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ChangeProductQuantityRequestDto;
import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "shopping-cart")
public interface ShoppingCartClient {
    @PutMapping
    public ShoppingCartDto addProductsInShoppingCart (@RequestParam String username,
                                                      @RequestBody Map<UUID, Long> products) throws FeignException;

    @GetMapping
    public ShoppingCartDto getActualShoppingCartByUser (@RequestParam String username) throws FeignException;

    @DeleteMapping
    public void deactivateShoppingCartByUser (@RequestParam String username) throws FeignException;

    @PostMapping("/remove")
    public ShoppingCartDto removeProductFromShoppingCart (@RequestParam String username,
                                                          @RequestBody List<UUID> products) throws FeignException;

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeProductQuantityInShoppingCart (@RequestParam String username,
                                                                @RequestBody ChangeProductQuantityRequestDto requestDto) throws FeignException;
}
