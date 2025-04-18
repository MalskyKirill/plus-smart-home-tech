package ru.yandex.practicum.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

@Data
public class Pageable {
    @Min(value = 0)
    private Integer page;
    @Min(value = 1)
    private Integer size;
    private List<String> sort;
}
