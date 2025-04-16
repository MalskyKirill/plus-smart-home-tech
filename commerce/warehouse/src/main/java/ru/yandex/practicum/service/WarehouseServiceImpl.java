package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.AddProductToWarehouseRequestDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequestDto;
import ru.yandex.practicum.exeption.NotFoundException;
import ru.yandex.practicum.exeption.ProductAlreadyExistException;
import ru.yandex.practicum.mapper.WarehouseProductMapper;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.repositore.WarehouseRepository;

import java.util.UUID;

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

    @Override
    public void addQuantity(AddProductToWarehouseRequestDto requestQuantityDto) {
        WarehouseProduct warehouseProduct = getWarehouseProduct(requestQuantityDto.getProductId());

        long newQuantity = warehouseProduct.getQuantity() + requestQuantityDto.getQuantity();
        warehouseProduct.setQuantity(newQuantity);
        log.info("теперь товара на складе {}шт", warehouseProduct.getQuantity());
        warehouseRepository.save(warehouseProduct);
    }

    private WarehouseProduct getWarehouseProduct(UUID productId) {
        return warehouseRepository.findById(productId).orElseThrow(
            () -> new NotFoundException("товара с таким id нет на складе")
        );
    }
}
