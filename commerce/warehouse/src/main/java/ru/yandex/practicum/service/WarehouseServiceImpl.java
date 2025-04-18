package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exeption.InsufficientProductException;
import ru.yandex.practicum.exeption.NotFoundException;
import ru.yandex.practicum.exeption.ProductAlreadyExistException;
import ru.yandex.practicum.mapper.WarehouseProductMapper;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.repositore.WarehouseRepository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;

    @Override
    @Transactional
    public void addNewProduct(NewProductInWarehouseRequestDto requestProductDto) {
        if (warehouseRepository.existsById(requestProductDto.getProductId())) {
            throw new ProductAlreadyExistException("товар с таким id уже зарегистрирован на складе");
        }

        log.info("сохраняем товар в на складе");
        warehouseRepository.save(WarehouseProductMapper.toWarehouseProduct(requestProductDto));
    }

    @Override
    @Transactional
    public void addQuantity(AddProductToWarehouseRequestDto requestQuantityDto) {
        WarehouseProduct warehouseProduct = getWarehouseProduct(requestQuantityDto.getProductId());

        long newQuantity = warehouseProduct.getQuantity() + requestQuantityDto.getQuantity();
        warehouseProduct.setQuantity(newQuantity);
        log.info("теперь товара на складе {}шт", warehouseProduct.getQuantity());
        warehouseRepository.save(warehouseProduct);
    }

    @Override
    public WarehouseAddressDto getAddress() {
        String warehouseAddress = AddressService.getAddress();
        return WarehouseAddressDto.builder()
            .country(warehouseAddress)
            .city(warehouseAddress)
            .street(warehouseAddress)
            .house(warehouseAddress)
            .flat(warehouseAddress)
            .build();
    }

    @Override
    @Transactional
    public BookedProductsDto checkQuantity(ShoppingCartDto shoppingCartDto) {
        Map<UUID, Long> cartProducts = shoppingCartDto.getProducts();
        Map<UUID, WarehouseProduct> products = warehouseRepository.findAllById(cartProducts.keySet())
            .stream()
            .collect(Collectors.toMap(WarehouseProduct::getProductId, Function.identity()));

        Set<UUID> cardProductIds = cartProducts.keySet();
        Set<UUID> productsIds = products.keySet();

        for (UUID cardProductId : cardProductIds) {
            if (!productsIds.contains(cardProductId)) {
                throw new NotFoundException("товара с id " + cardProductId + " нет на складе");
            }
        }

        cartProducts.forEach((key, value) -> {
            WarehouseProduct product = products.get(key);
            if(product.getQuantity() < value) {
                throw new InsufficientProductException("товара с id " + key + " нехватает на складе");
            }
        });

        double weight = 0;
        double volume = 0;
        boolean fragile = false;

        for (Map.Entry<UUID, Long> cartProduct : cartProducts.entrySet()) {
            WarehouseProduct product = products.get(cartProduct.getKey());

            weight += product.getWeight() * cartProduct.getValue();
            volume += product.getHeight() * product.getWeight() * product.getDepth() * cartProduct.getValue();
            fragile = fragile || product.isFragile();
        }

        return new BookedProductsDto(weight, volume, fragile);
    }

    private WarehouseProduct getWarehouseProduct(UUID productId) {
        return warehouseRepository.findById(productId).orElseThrow(
            () -> new NotFoundException("товара с таким id нет на складе")
        );
    }
}
