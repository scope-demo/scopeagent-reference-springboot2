package com.undefinedlabs.scope.controller;

import com.google.common.util.concurrent.Uninterruptibles;
import com.undefinedlabs.scope.model.dto.CarPlateDTO;
import com.undefinedlabs.scope.service.CarPlateRequest;
import com.undefinedlabs.scope.service.CarPlateResponse;
import com.undefinedlabs.scope.service.CarPlateServiceGrpc;
import com.undefinedlabs.scope.service.CarPlateServiceImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.context.LocalRunningGrpcPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/carplate")
public class CarPlateController {

    private final CarPlateServiceImpl carPlateService;

    @LocalRunningGrpcPort
    private int grpcLocalServerPort;


    @Autowired
    public CarPlateController(final CarPlateServiceImpl carPlateService) {
        this.carPlateService = carPlateService;
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public CarPlateDTO getByUuid(@PathVariable(name="uuid") String uuid){

        final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", grpcLocalServerPort)
                .usePlaintext()
                .build();

        final CarPlateServiceGrpc.CarPlateServiceStub stub = CarPlateServiceGrpc.newStub(channel);

        final CarPlateResponse[] response = new CarPlateResponse[1];
        final Throwable[] exception = new Throwable[1];
        final CountDownLatch finishLatch = new CountDownLatch(1);
        final StreamObserver<CarPlateResponse> responseObserver = new StreamObserver<CarPlateResponse>() {
            @Override
            public void onNext(CarPlateResponse value) {
                response[0] = value;
            }

            @Override
            public void onError(Throwable t) {
                exception[0] = t;
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        stub.getCarPlate(CarPlateRequest.newBuilder().setUuid(uuid).build(), responseObserver);

        if (!Uninterruptibles.awaitUninterruptibly(finishLatch, 10, TimeUnit.SECONDS)) {
            throw new RuntimeException("timeout!");
        }

        if(exception[0] != null){
            throw new RuntimeException(exception[0]);
        }

        channel.shutdown();

        return new CarPlateDTO(response[0].getCarPlate());
    }

    @RequestMapping(value = "/multiple/{uuid}", method = RequestMethod.GET)
    public List<CarPlateDTO> getByUuidMultiple(@PathVariable(name="uuid") String uuid){

        final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", grpcLocalServerPort)
                .usePlaintext()
                .build();

        final CarPlateServiceGrpc.CarPlateServiceStub stub = CarPlateServiceGrpc.newStub(channel);

        final CountDownLatch finishLatch = new CountDownLatch(1);
        final List<CarPlateResponse> responses = new ArrayList<>();
        final List<Throwable> throwables = new ArrayList<>();
        final StreamObserver<CarPlateResponse> responseObserver = new StreamObserver<CarPlateResponse>() {
            @Override
            public void onNext(CarPlateResponse value) {
                responses.add(value);
            }

            @Override
            public void onError(Throwable t) {
                throwables.add(t);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                finishLatch.countDown();
            }
        };

        final CarPlateRequest carPlateRequest = CarPlateRequest.newBuilder().setUuid(uuid).build();
        final StreamObserver<CarPlateRequest> requestStream = stub.getCarPlates(responseObserver);
        requestStream.onNext(carPlateRequest);
        requestStream.onNext(carPlateRequest);
        requestStream.onNext(carPlateRequest);
        requestStream.onCompleted();

        if (!Uninterruptibles.awaitUninterruptibly(finishLatch, 10, TimeUnit.SECONDS)) {
            throw new RuntimeException("timeout!");
        }

        final List<CarPlateDTO> carPlateDTOS = new ArrayList<>();
        for(CarPlateResponse response : responses) {
            carPlateDTOS.add(new CarPlateDTO(response.getCarPlate()));
        }

        channel.shutdown();
        return carPlateDTOS;
    }

}
