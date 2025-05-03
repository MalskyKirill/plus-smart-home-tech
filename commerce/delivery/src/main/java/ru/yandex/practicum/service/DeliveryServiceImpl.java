package ru.yandex.practicum.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.client.WarehouseClient;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ShippedToDeliveryRequestDto;
import ru.yandex.practicum.dto.enums.DeliveryState;
import ru.yandex.practicum.exeption.NotFoundException;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.repository.DeliveryRepository;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{
    private final DeliveryRepository deliveryRepository;
    private final WarehouseClient warehouseClient;

    private final double BASE_DELIVERY_PRICE = 5.0;
    private final double ADDRESS_1_RATIO = 1;
    private final double ADDRESS_2_RATIO = 2;
    private final double DIFF_STREET_RATIO = 1.2;
    private final double FRAGILE_RATIO = 1.2;


    @Override
    @Transactional
    public DeliveryDto create(DeliveryDto deliveryDto) {
        log.info("сохраняем доствку в базе данных");
        return DeliveryMapper.toDeliveryDto(deliveryRepository.save(DeliveryMapper.toDelivery(deliveryDto)));
    }

    @Override
    @Transactional
    public Double cost(OrderDto orderDto) {
        Delivery delivery = getDeliveryByOrderId(orderDto.getOrderId());
        delivery.setDeliveryWeight(orderDto.getDeliveryWeight());
        delivery.setDeliveryWeight(orderDto.getDeliveryVolume());
        delivery.setFragile(orderDto.getFragile());
        log.info("начинаем подсчет доставки");

        Double cost = BASE_DELIVERY_PRICE;
        cost += BASE_DELIVERY_PRICE * getRatioByFromAddress(delivery.getFromAddress());
        cost *= getRatioByFragile(delivery.getFragile());
        cost += delivery.getDeliveryVolume() * 0.2;
        cost += delivery.getDeliveryWeight() * 0.3;
        cost *= getRatioByToAddress(delivery.getFromAddress(), delivery.getToAddress());
        log.info("стоимость доставки " + cost);
        return cost;
    }

    @Override
    @Transactional
    public void picked(UUID deliveryId) {
        Delivery delivery = getDeliveryById(deliveryId);

        log.info("передаем заказ на склад для проверки товаров");
        try {
            warehouseClient.shippedDelivery(getShippedToDeliveryRequest(delivery));

        } catch (FeignException ex) {
            log.error("ошибка при обработке заказа в сервисе warehouse");
            throw ex;
        }

        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
    }

    private ShippedToDeliveryRequestDto getShippedToDeliveryRequest(Delivery delivery) {
        return ShippedToDeliveryRequestDto
            .builder()
            .orderId(delivery.getOrderId())
            .deliveryId(delivery.getDeliveryId())
            .build();
    }

    private Delivery getDeliveryById(UUID deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new NotFoundException("доставки с id " + deliveryId + "нет"))
    }

    double getRatioByFromAddress(Address address) {
        String addressStr = address.toString();
        if (addressStr.contains("ADDRESS_1")) {
            return ADDRESS_1_RATIO;
        }

        return ADDRESS_2_RATIO;
    }

    double getRatioByFragile(boolean isFragile) {
        if (isFragile) {
            return FRAGILE_RATIO;
        }

        return 1.0;
    }

    double getRatioByToAddress(Address from, Address to) {
        if (!from.getStreet().equals(to.getStreet())) {
            return DIFF_STREET_RATIO;
        }

        return 1.0;
    }

    private Delivery getDeliveryByOrderId(UUID orderId) {
        return deliveryRepository.findByOrderId(orderId).orElseThrow(() -> new NotFoundException("доставки у заказа с id " + orderId + "нет"));
    }
}
