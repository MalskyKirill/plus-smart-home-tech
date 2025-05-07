package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.model.Delivery;

public class DeliveryMapper {
    public static DeliveryDto toDeliveryDto(Delivery delivery) {
        return DeliveryDto.builder()
            .deliveryId(delivery.getDeliveryId())
            .fromAddress(AddressMapper.toAddressDto(delivery.getFromAddress()))
            .toAddress(AddressMapper.toAddressDto(delivery.getToAddress()))
            .orderId(delivery.getOrderId())
            .deliveryState(delivery.getDeliveryState())
            .build();
    }

    public static Delivery toDelivery(DeliveryDto deliveryDto) {
        return Delivery.builder()
            .orderId(deliveryDto.getOrderId())
            .fromAddress(AddressMapper.toAddress(deliveryDto.getFromAddress()))
            .toAddress(AddressMapper.toAddress(deliveryDto.getToAddress()))
            .deliveryState(deliveryDto.getDeliveryState())
            .build();
    }
}
