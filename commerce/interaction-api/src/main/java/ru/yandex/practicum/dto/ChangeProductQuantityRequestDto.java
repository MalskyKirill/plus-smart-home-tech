package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ChangeProductQuantityRequestDto {
    @NotNull
    public UUID productId;
    @NotNull
    private Long newQuantity;
}
