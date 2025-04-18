package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class DimensionDto {
    @NotNull
    @Min(value = 1)
    private Double width;
    @NotNull
    @Min(value = 1)
    private Double height;
    @NotNull
    @Min(value = 1)
    private double depth;
}
