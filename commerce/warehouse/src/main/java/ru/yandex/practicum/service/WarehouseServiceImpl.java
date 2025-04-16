package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.NewProductInWarehouseRequestDto;
import ru.yandex.practicum.exeption.ProductAlreadyExistException;
import ru.yandex.practicum.mapper.WarehouseProductMapper;
import ru.yandex.practicum.repositore.WarehouseRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    @Override
    public void addNewProduct(NewProductInWarehouseRequestDto requestProductDto) {
        if (warehouseRepository.existsById(requestProductDto.getProductId())) {
            throw new ProductAlreadyExistException("товар с таким id уже зарегистрирован на складе");
        }

        log.info("сохраняем товар в на складе");
        warehouseRepository.save(WarehouseProductMapper.toWarehouseProduct(requestProductDto));
    }
}
