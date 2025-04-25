package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.CreateNewOrderRequestDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.enums.OrderState;
import ru.yandex.practicum.model.Order;

public class OrderMapper {
    public static Order toOrder(CreateNewOrderRequestDto requestDto, BookedProductsDto bookedProductsDto) {
        return Order.builder()
            .userName(requestDto.getUserName())
            .cartId(requestDto.getShoppingCartDto().getCardId())
            .products(requestDto.getShoppingCartDto().getProducts())
            .state(OrderState.NEW)
            .deliveryVolume(bookedProductsDto.getDeliveryVolume())
            .deliveryWeight(bookedProductsDto.getDeliveryWeight())
            .fragile(bookedProductsDto.getFragile())
            .build();
    }

    public static OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
            .orderId(order.getOrderId())
            .cartId(order.getCartId())
            .products(order.getProducts())
            .paymentId(order.getPaymentId())
            .deliveryId(order.getDeliveryId())
            .state(order.getState())
            .deliveryWeight(order.getDeliveryWeight())
            .deliveryVolume(order.getDeliveryVolume())
            .fragile(order.getFragile())
            .totalPrice(order.getTotalPrice())
            .deliveryPrice(order.getDeliveryPrice())
            .productPrice(order.getProductPrice())
            .build();
    }
}
