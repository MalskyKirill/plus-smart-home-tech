package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.model.Product;

public class ProductMapper {
    public static Product toProduct(ProductDto productDto) {
        return Product.builder()
            .productId(productDto.getProductId())
            .productName(productDto.getProductName())
            .description(productDto.getDescription())
            .imageSrc(productDto.getImageSrc())
            .quantityState(productDto.getQuantityState())
            .productState(productDto.getProductState())
            .productCategory(productDto.getProductCategory())
            .price(productDto.getPrice())
            .build();
    }

    public static ProductDto toProductDto(Product product) {
        return ProductDto.builder()
            .productId(product.getProductId())
            .productName(product.getProductName())
            .description(product.getDescription())
            .imageSrc(product.getImageSrc())
            .quantityState(product.getQuantityState())
            .productState(product.getProductState())
            .productCategory(product.getProductCategory())
            .price(product.getPrice())
            .build();
    }
}
