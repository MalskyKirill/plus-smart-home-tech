package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.repository.DeliveryRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService{
    private final DeliveryRepository deliveryRepository;
    @Override
    @Transactional
    public DeliveryDto create(DeliveryDto deliveryDto) {
        log.info("сохраняем доствку в базе данных");
        return DeliveryMapper.toDeliveryDto(deliveryRepository.save(DeliveryMapper.toDelivery(deliveryDto)));
    }
}
