package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class ShoppingCartDto {
    private UUID cardId;
    private Map<UUID, Long> products;
}
