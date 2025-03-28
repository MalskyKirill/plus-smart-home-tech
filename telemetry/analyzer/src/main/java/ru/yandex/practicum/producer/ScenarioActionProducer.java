package ru.yandex.practicum.producer;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.model.Action;

import java.time.Instant;



@Slf4j
@Service
public class ScenarioActionProducer {
    private final HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterStub;

    public ScenarioActionProducer(
        @GrpcClient("hub-router") HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterStub) {
        this.hubRouterStub = hubRouterStub;
    }

    public void sendAction(Action action) {
        DeviceActionProto deviceActionProto = DeviceActionProto.newBuilder()
            .setSensorId(action.getSensor().getId())
            .setType(ActionTypeProto.valueOf(action.getType().name()))
            .setValue(action.getValue())
            .build();

        Instant instant = Instant.now();

        Timestamp timestamp = Timestamp.newBuilder()
            .setSeconds(instant.getEpochSecond())
            .setNanos(instant.getNano())
            .build();

        DeviceActionRequest request = DeviceActionRequest.newBuilder()
            .setHubId(action.getScenario().getHubId())
            .setScenarioName(action.getScenario().getName())
            .setTimestamp(timestamp)
            .setAction(deviceActionProto)
            .build();

        hubRouterStub.handleDeviceAction(request);
        log.info("экшен {} отправлен в hub-router", request);



    }
}
