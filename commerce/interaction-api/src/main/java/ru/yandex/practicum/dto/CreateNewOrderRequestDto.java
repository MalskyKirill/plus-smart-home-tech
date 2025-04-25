package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNewOrderRequestDto {
    @NotNull
    private ShoppingCartDto shoppingCartDto;
    @NotNull
    private DeliveryAddressDto deliveryAddressDto;
    @NotNull
    private String userName;
}
