package ru.yandex.practicum.model.hub;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.model.enums.ConditionType;
import ru.yandex.practicum.model.enums.OperationType;

@Getter
@Setter
@ToString
public class ScenarioCondition {
    private String sensorId;
    private ConditionType type;
    private OperationType operation;
    private Integer value;
}
