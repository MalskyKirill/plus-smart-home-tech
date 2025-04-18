package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.NewProductInWarehouseRequestDto;
import ru.yandex.practicum.model.WarehouseProduct;

public class WarehouseProductMapper {
    public static WarehouseProduct toWarehouseProduct (NewProductInWarehouseRequestDto requestDto) {
        return WarehouseProduct.builder()
            .productId(requestDto.getProductId())
            .fragile(checkFragile(requestDto.getFragile()))
            .width(requestDto.getDimension().getWidth())
            .height(requestDto.getDimension().getHeight())
            .depth(requestDto.getDimension().getDepth())
            .weight(requestDto.getWeight())
            .quantity(0)
            .build();
    }

    private static boolean checkFragile(Boolean fragile) {
        return fragile != null && fragile;
    }
}
