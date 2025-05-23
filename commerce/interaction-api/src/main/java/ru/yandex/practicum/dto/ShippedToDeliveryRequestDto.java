package ru.yandex.practicum.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ShippedToDeliveryRequestDto {
    @NotNull
    private UUID orderId;
    @NotNull
    private UUID deliveryId;
}
