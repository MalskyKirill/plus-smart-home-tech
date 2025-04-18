package ru.yandex.practicum.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.AddProductToWarehouseRequestDto;
import ru.yandex.practicum.dto.NewProductInWarehouseRequestDto;
import ru.yandex.practicum.dto.WarehouseAddressDto;

@FeignClient(name = "warehouse")
public interface WarehouseClient {
    @PutMapping
    public void addNewProductInWarehouse(@Valid @RequestBody NewProductInWarehouseRequestDto requestProductDto);

    @PostMapping("/add")
    public void addQuantityProductInWarehouse(@Valid @RequestBody AddProductToWarehouseRequestDto requestQuantityDto);

    @GetMapping("/address")
    public WarehouseAddressDto getWarehouseAddress();
}
