package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.exeption.NotFoundException;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.ProductRepository;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShoppingStoreServiceImpl implements ShoppingStoreService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductDto create(ProductDto productDto) {
        log.info("сохраняем товар в базе данных");
        return ProductMapper.toProductDto(productRepository.save(ProductMapper.toProduct(productDto)));
    }

    @Override
    public ProductDto getProduct(UUID productId) {
        log.info("получаем товар из базы");
        return ProductMapper.toProductDto(findProductById(productId));
    }

    @Override
    @Transactional
    public ProductDto update(ProductDto productDto) {
        Product oldProduct = findProductById(productDto.getProductId());
        productRepository.deleteById(oldProduct.getProductId());

        Product updateProduct = productRepository.save(ProductMapper.toProduct(productDto));
        log.info("обнавляем товар в базе данных");
        return ProductMapper.toProductDto(updateProduct);
    }


    private Product findProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("продукт " + productId + "не найден"));
    }
}
