package ru.yandex.practicum.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.DeliveryClient;
import ru.yandex.practicum.client.PaymentClient;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.dto.enums.DeliveryState;
import ru.yandex.practicum.dto.enums.OrderState;
import ru.yandex.practicum.exeption.MissingUsernameException;
import ru.yandex.practicum.exeption.NotFoundException;
import ru.yandex.practicum.exeption.ValidationException;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.repository.OrderRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;

    @Override
    @Transactional
    public OrderDto create(CreateNewOrderRequestDto requestDto) {
        checkUserName(requestDto.getUserName());

        log.info("проверяем наличие товара на складе");
        try {
            BookedProductsDto bookedProductsDto = warehouseClient.checkProductsQuantity(requestDto.getShoppingCartDto());
            log.info("проверка количества товара на складе прошла успешно");

            Order newOrder = orderRepository.save(OrderMapper.toOrder(requestDto, bookedProductsDto));
            System.out.println(newOrder.toString());
            UUID deliveryId = getDeliveryId(newOrder.getOrderId(), requestDto.getDeliveryAddressDto());

            newOrder.setDeliveryId(deliveryId);
            return OrderMapper.toOrderDto(newOrder);

        } catch (FeignException ex) {
            log.error("ошибка при проверке количества товара на складе");
            throw ex;
        }
    }

    @Override
    public List<OrderDto> get(String username) {
        log.info("получаем все заказы пользователя");

        List<Order> orders = orderRepository.findAllByUserName(username);

        return orders.stream().map(OrderMapper::toOrderDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDto returnOrder(ProductReturnRequestDto productReturnRequestDto) {
        Order order = getOrderById(productReturnRequestDto.getOrderId());

        if(order.getState().equals(OrderState.NEW)
            || order.getState().equals(OrderState.CANCELED)
            || order.getState().equals(OrderState.PRODUCT_RETURNED)) {
            throw new ValidationException("невозможно вернуть заказ");
        }

        Map<UUID, Long> orderProducts = order.getProducts();
        Map<UUID, Long> returnProducts = productReturnRequestDto.getProducts();

        log.info("проверка возвращаемых товаров");
        orderProducts.forEach((key, value) -> {
            Long returnQuantity = returnProducts.get(key);

            if (returnQuantity == null) {
                throw new ValidationException("Товар с id " + key +" отсутствует в списке возвратов");
            }

            if (!Objects.equals(value, returnQuantity)) {
                String message = String.format("Не совпадает количество товара с id = %d: заказано %d, возвращается %d",
                    key, value, returnQuantity);
                throw new ValidationException(message);
            }
        });

        log.info("начинаем возврат товаров на склад");
        try {
            warehouseClient.returnProductsToWarehouse(returnProducts);
        } catch (FeignException ex) {
            log.error("ошибка при возврате товаров на склад");
            throw ex;
        }

        log.info("товары успешно возвращены на склад");
        order.setState(OrderState.PRODUCT_RETURNED);
        return OrderMapper.toOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto payment(UUID orderId) {
        Order order = getOrderById(orderId);
        if (order.getState().equals(OrderState.PAID)) {
            throw new ValidationException("заказ уже оплачен");
        }

        if (order.getState().equals(OrderState.ON_PAYMENT)) {
            log.info("оплата прошла успешно");
            order.setState(OrderState.PAID);
            return OrderMapper.toOrderDto(order);
        }

        if (!order.getState().equals(OrderState.ASSEMBLED)) {
            throw new ValidationException("заказ еще не собран");
        }

        log.info("отправляем заказ на оплату");
        try {
            order.setState(OrderState.ON_PAYMENT);
            PaymentDto paymentDto = paymentClient.createPayment(OrderMapper.toOrderDto(order));
            order.setPaymentId(paymentDto.getPaymentId());
        } catch (FeignException ex) {
            log.error("ошибка при оплате заказа");
            throw ex;
        }

        return OrderMapper.toOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto paymentFailed(UUID orderId) {
        Order order = getOrderById(orderId);

        log.info("меняем статус заказа на PAYMENT_FAILED");
        order.setState(OrderState.PAYMENT_FAILED);

        return OrderMapper.toOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto completed(UUID orderId) {
        Order order = getOrderById(orderId);

        log.info("меняем статус заказа на COMPLETED");
        order.setState(OrderState.COMPLETED);

        return OrderMapper.toOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateDelivery(UUID orderId) {
        Order order = getOrderById(orderId);

        log.info("расчитываем стоимость доставки");
        try {
            Double deliveryPryce = deliveryClient.calculateDeliveryCost(OrderMapper.toOrderDto(order));
            order.setDeliveryPrice(deliveryPryce);
        } catch (FeignException ex) {
            log.error("ошибка при расчете доставки");
            throw ex;
        }

        return OrderMapper.toOrderDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateTotal(UUID orderId) {
        Order order = getOrderById(orderId);

        log.info("начинаем рассчет общей стоимости");
        try {
            log.info("начинаем расчет стоимости товаров в заказе");
            Double productsPrice = paymentClient.calculateProductCost(OrderMapper.toOrderDto(order));
            order.setProductPrice(productsPrice);

            log.info("начинаем расчет общей стоимости");
            Double totalPrice = paymentClient.calculateTotalCost(OrderMapper.toOrderDto(order));
            order.setTotalPrice(totalPrice);

        } catch (FeignException ex) {
            log.error("ошибка при расчете общей стоимости");
            throw ex;
        }

        return OrderMapper.toOrderDto(order);
    }

    private UUID getDeliveryId(UUID orderId, AddressDto addressDto) {
        log.info("создаем доставку для заказа с id {}", orderId);
        DeliveryDto deliveryDto = DeliveryDto.builder()
            .fromAddress(warehouseClient.getWarehouseAddress())
            .toAddress(addressDto)
            .orderId(orderId)
            .deliveryState(DeliveryState.CREATED)
            .build();

        return deliveryClient.createNewDelivery(deliveryDto).getDeliveryId();
    }

    private void checkUserName(String username) {
        if(username.isBlank()) {
            throw new MissingUsernameException("отсутствует имя пользователя");
        }
    }

    private Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("заказа с id " + orderId + "нет"));
    }
}
