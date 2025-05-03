package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.dto.enums.PaymentState;
import ru.yandex.practicum.model.Payment;

public class PaymentMapper {
    public static Double FEE_TAX = 0.1;
    public static Payment toPayment(OrderDto orderDto) {
        return Payment.builder()
            .orderId(orderDto.getOrderId())
            .paymentState(PaymentState.PENDING)
            .totalPayment(orderDto.getTotalPrice())
            .deliveryTotal(orderDto.getDeliveryPrice())
            .feeTotal(orderDto.getProductPrice() * FEE_TAX)
            .build();
    }

    public static PaymentDto toPaymentDto(Payment payment) {
        return PaymentDto.builder()
            .paymentId(payment.getPaymentId())
            .totalPayment(payment.getTotalPayment())
            .deliveryTotal(payment.getDeliveryTotal())
            .feeTotal(payment.getFeeTotal())
            .build();
    }
}
