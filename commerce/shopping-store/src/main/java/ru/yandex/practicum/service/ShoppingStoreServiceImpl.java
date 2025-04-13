package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.repository.ProductRepository;

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
}
