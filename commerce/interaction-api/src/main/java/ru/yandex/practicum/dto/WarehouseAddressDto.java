package ru.yandex.practicum.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WarehouseAddressDto {
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
}
