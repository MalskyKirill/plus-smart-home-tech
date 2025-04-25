package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.AddressDto;
import ru.yandex.practicum.model.Address;

public class AddressMapper {
    public static AddressDto toAddressDto(Address address) {
        return AddressDto.builder()
            .country(address.getCountry())
            .city(address.getCity())
            .street(address.getStreet())
            .house(address.getHouse())
            .flat(address.getFlat())
            .build();
    }

    public static Address toAddress(AddressDto addressDto) {
        return Address.builder()
            .country(addressDto.getCountry())
            .city(addressDto.getCity())
            .street(addressDto.getStreet())
            .house(addressDto.getHouse())
            .flat(addressDto.getFlat())
            .build();
    }
}
