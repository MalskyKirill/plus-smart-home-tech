package ru.yandex.practicum.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.DeliveryClient;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.dto.enums.DeliveryState;
import ru.yandex.practicum.exeption.MissingUsernameException;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.repository.OrderRepository;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final WarehouseClient warehouseClient;
    private final DeliveryClient deliveryClient;

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
}
