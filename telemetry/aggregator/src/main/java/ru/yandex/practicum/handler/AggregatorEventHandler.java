package ru.yandex.practicum.handler;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AggregatorEventHandler {
    Map<String, SensorsSnapshotAvro> snapshots = new HashMap<>();

    public Optional<SensorsSnapshotAvro> updateState(SensorEventAvro event) {
        String hubId = event.getHubId(); // взяли айдишник хаба у пришедшего эвента

        SensorsSnapshotAvro snapshot = snapshots.getOrDefault(hubId, createSensorSnapshotAvro(hubId)); // проверили есть ли снапшот в мапе если нет создали новый
        SensorStateAvro oldState = snapshot.getSensorsState().get(event.getId()); // взяли старое сотояние снепшота

        if (oldState != null // если оно есть
                && oldState.getTimestamp().isAfter(event.getTimestamp()) // и если оно случилось после текущего события
                && oldState.getData().equals(event.getPayload())) { // и если его даные равны данным текущего события
            return Optional.empty(); // вернем пустой опшин
        }

        SensorStateAvro newState = createSensorStateAvro(event); // создаем новое состояние на основе данных события
        snapshot.getSensorsState().put(event.getId(), newState); //добавляем его в снапшот
        snapshot.setTimestamp(event.getTimestamp()); // обновляем таймстемп снапшота таймстемпом из события
        snapshots.put(hubId, snapshot); // записываем снапшот в мапу
        return Optional.of(snapshot); // возвращаем снапшот
    }

    private SensorsSnapshotAvro createSensorSnapshotAvro(String hubId) {
        return SensorsSnapshotAvro.newBuilder()
            .setHubId(hubId)
            .setTimestamp(Instant.now())
            .setSensorsState(new HashMap<>())
            .build();
    }


    private SensorStateAvro createSensorStateAvro(SensorEventAvro event) {
        return SensorStateAvro.newBuilder()
            .setTimestamp(event.getTimestamp())
            .setData(event.getPayload())
            .build();
    }
}
