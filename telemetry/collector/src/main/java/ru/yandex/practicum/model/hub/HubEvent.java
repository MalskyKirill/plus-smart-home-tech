package ru.yandex.practicum.model.hub;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.model.enums.HubEventType;

import java.time.Instant;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
                include = JsonTypeInfo.As.EXISTING_PROPERTY,
                property = "type",
                defaultImpl = HubEventType.class)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DeviceAddedEvent.class, name = "DEVICE_ADDED_EVENT"),
    @JsonSubTypes.Type(value = DeviceRemoveEvent.class, name = "DEVICE_REMOVE_EVENT"),
    @JsonSubTypes.Type(value = ScenarioAddedEvent.class, name = "SCENARIO_ADDED_EVENT"),
    @JsonSubTypes.Type(value = ScenarioRemovedEvent.class, name = "SCENARIO_REMOVED_EVENT")})
@Getter
@Setter
@ToString
public abstract class HubEvent {
    @NotBlank
    private String hubId;
    private Instant timestamp = Instant.now();

    @NotNull
    public abstract HubEventType getType();
}
