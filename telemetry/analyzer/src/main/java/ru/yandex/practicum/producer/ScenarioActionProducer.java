package ru.yandex.practicum.producer;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.model.Action;
import ru.yandex.practicum.model.Enum.ActionType;
import ru.yandex.practicum.model.Scenario;

import java.time.Instant;

@Slf4j
@Service
public class ScenarioActionProducer {
    @GrpcClient("hub-router")
    private HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    public void sendAction(Scenario scenario) {
        try {
            log.info("поступил сценарий {}", scenario.getName());
            String hubId = scenario.getHubId();
            String scenarioName = scenario.getName();
            for (Action action : scenario.getActions()) {
                log.info("actiontype {}", action.getType().name());
                log.info("action {}", action.getId());
                DeviceActionProto deviceActionProto = DeviceActionProto.newBuilder()
                    .setSensorId(action.getSensor().getId())
                    .setType(toActionType(action.getType()))
                    .setValue(action.getValue())
                    .build();

                log.info("deviceActionProto.type {}", deviceActionProto.getType());
                log.info("deviceActionProto {}", deviceActionProto.toString());

                Instant instant = Instant.now();

                Timestamp timestamp = Timestamp.newBuilder()
                    .setSeconds(instant.getEpochSecond())
                    .setNanos(instant.getNano())
                    .build();

                DeviceActionRequest request = DeviceActionRequest.newBuilder()
                    .setHubId(hubId)
                    .setScenarioName(scenarioName)
                    .setAction(deviceActionProto)
                    .setTimestamp(timestamp)
                    .build();
                log.info("request {}", request.toString());



                hubRouterClient.handleDeviceAction(request);
                log.info("экшен {} отправлен в hub-router", request.getScenarioName());
            }

        } catch (Exception e) {
            log.error("Ошибка при отправке действия: {}", e.getMessage(), e);
            throw e;
        }
    }

    private ActionTypeProto toActionType(ActionType actionType) {
        log.info("тип действия {}", actionType);
        return switch (actionType) {
            case ACTIVATE -> ActionTypeProto.ACTIVATE;
            case DEACTIVATE -> ActionTypeProto.DEACTIVATE;
            case INVERSE -> ActionTypeProto.INVERSE;
            case SET_VALUE -> ActionTypeProto.SET_VALUE;
        };
    }
}
