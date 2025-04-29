package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.exeption.ValidationException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.repository.PaymentRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDto create(OrderDto orderDto) {
        if (orderDto.getOrderId() == null) {
            throw new ValidationException("нет id заказа");
        }

        log.info("сохраняем оплату в бд");
        return PaymentMapper.toPaymentDto(paymentRepository.save(PaymentMapper.toPayment(orderDto)));
    }
}
