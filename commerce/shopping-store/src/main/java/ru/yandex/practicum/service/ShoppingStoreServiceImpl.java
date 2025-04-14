package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.Pageable;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.enums.ProductCategory;
import ru.yandex.practicum.dto.enums.ProductState;
import ru.yandex.practicum.exeption.NotFoundException;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.ProductRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public boolean delete(UUID productId) {
        Product oldProduct = findProductById(productId);

        if (oldProduct.getProductState().equals(ProductState.DEACTIVATE)) {
            log.info("продукт уже удален");
            return false;
        }

        log.info("удаляем продукт");
        oldProduct.setProductState(ProductState.DEACTIVATE);
        return true;
    }

    @Override
    @Transactional
    public boolean setQuantityState(SetProductQuantityStateRequest request) {
        Product oldProduct = findProductById(request.getProductId());

        if (oldProduct.getQuantityState().equals(request.getQuantityState())) {
            log.info("количество не поменялось");
            return false;
        }

        log.info("изменяем количество продукта");
        oldProduct.setQuantityState(request.getQuantityState());
        productRepository.save(oldProduct);
        return true;
    }

    @Override
    public List<ProductDto> getProducts(ProductCategory productCategory, Pageable pageable) {
        Sort sort = Sort.by(pageable.getSort().getFirst());
        PageRequest pageRequest = PageRequest.of(pageable.getPage(), pageable.getSize(), sort);

        log.info("получаем из бд список товаров в пагинированном виде");
        return productRepository.findAllByProductCategory(productCategory, pageRequest).stream()
            .map(ProductMapper::toProductDto).collect(Collectors.toList());
    }


    private Product findProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("продукт " + productId + "не найден"));
    }
}
