package ru.yandex.practicum.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.OrderClient;
import ru.yandex.practicum.client.ShoppingStoreClient;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.enums.PaymentState;
import ru.yandex.practicum.exeption.NotFoundException;
import ru.yandex.practicum.exeption.ValidationException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.repository.PaymentRepository;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    private final PaymentRepository paymentRepository;
    private final ShoppingStoreClient shoppingStoreClient;
    private final OrderClient orderClient;

    @Override
    @Transactional
    public PaymentDto create(OrderDto orderDto) {
        if (orderDto.getOrderId() == null) {
            throw new ValidationException("нет id заказа");
        }

        log.info("сохраняем оплату в бд");
        return PaymentMapper.toPaymentDto(paymentRepository.save(PaymentMapper.toPayment(orderDto)));
    }

    @Override
    @Transactional
    public Double productCost(OrderDto orderDto) {
        Map<UUID, Long> products = orderDto.getProducts();

        try {
            log.info("из модуля shopping-store получаем стоимость товаров");
            Map<UUID, Float> productPrice = products
                .keySet()
                .stream()
                .map(shoppingStoreClient::getProductById)
                .collect(Collectors.toMap(ProductDto::getProductId, ProductDto::getPrice));

            log.info("начинаем подсчет стоимости товаров");

            return products
                .entrySet()
                .stream()
                .map(product -> product.getValue() * productPrice.get(product.getKey()))
                .mapToDouble(Float::floatValue)
                .sum();

        } catch (FeignException ex) {
            log.error("ошибка при получении данных из shoppingStore");
            throw ex;
        }
    }

    @Override
    @Transactional
    public Double totalCost(OrderDto orderDto) {
        log.info("начинаем подсчет общей стоимости");

        if(orderDto.getProductPrice() == null || orderDto.getTotalPrice() == null) {
            throw new ValidationException("нехватает данных для расчета");
        }

        return orderDto.getDeliveryPrice()
            + orderDto.getProductPrice()
            + (orderDto.getProductPrice() * PaymentMapper.FEE_TAX);
    }

    @Override
    public void refund(UUID paymentId) {
        Payment payment = getPaymentById(paymentId);
        payment.setPaymentState(PaymentState.SUCCESS);
        try {
            orderClient.paymentOrder(payment.getOrderId());
        } catch (FeignException ex) {
            log.error("ошибка при запросе к сервису order на изменение статуса оплыты");
            throw ex;
        }
    }

    private Payment getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
            .orElseThrow(() -> new NotFoundException("оплаты с id " + paymentId + "нет"));
    }
}
