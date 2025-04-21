package ru.yandex.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookedProductsDto {
    Double deliveryWeight;
    Double deliveryVolume;
    Boolean fragile;
}
