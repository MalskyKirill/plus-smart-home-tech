package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class NewProductInWarehouseRequestDto {
    @NotNull
    private UUID productId;
    private Boolean fragile;
    @NotNull
    private DimensionDto dimension;
    @NotNull
    @Min(value = 1)
    private Double weight;
}
