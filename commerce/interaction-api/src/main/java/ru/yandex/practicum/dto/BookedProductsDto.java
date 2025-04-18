package ru.yandex.practicum.dto;

import lombok.Data;

@Data
public class BookedProductsDto {
    Double deliveryWeight;
    Double deliveryVolume;
    Boolean fragile;
}
