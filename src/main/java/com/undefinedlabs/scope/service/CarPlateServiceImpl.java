package com.undefinedlabs.scope.service;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class CarPlateServiceImpl extends com.undefinedlabs.scope.service.CarPlateServiceGrpc.CarPlateServiceImplBase {

    private static final String SUCCESS_CAR_UUID = "9E219725-490E-4509-A42D-D0388DF317D4";
    private static final String NOT_FOUND_CAR_UUID = "asd";
    private static final String ERROR_CAR_UUID = "9E219725-490E-4509-A42D-D0388DF317DG";

    private static final String SUCCESS_CAR_PLATE = "1234-KFG";

    @Override
    public void getCarPlate(com.undefinedlabs.scope.service.CarPlateRequest request, StreamObserver<com.undefinedlabs.scope.service.CarPlateResponse> responseObserver) {
        final String uuid = request.getUuid();
        switch (uuid) {
            case SUCCESS_CAR_UUID:
                responseObserver.onNext(com.undefinedlabs.scope.service.CarPlateResponse.newBuilder().setCarPlate(SUCCESS_CAR_PLATE).build());
                responseObserver.onCompleted();
                break;
            case NOT_FOUND_CAR_UUID:
                responseObserver.onError(Status.NOT_FOUND.withDescription("Car Plate not found").asRuntimeException());
                break;
            case ERROR_CAR_UUID:
                responseObserver.onError(Status.INTERNAL.withDescription("Internal Server Error").asRuntimeException());
                break;
            default:
                responseObserver.onCompleted();
        }
    }

    @Override
    public StreamObserver<CarPlateRequest> getCarPlates(final StreamObserver<CarPlateResponse> responseObserver) {
        return new StreamObserver<CarPlateRequest>() {
            @Override
            public void onNext(CarPlateRequest carPlateRequest) {
                responseObserver.onNext(com.undefinedlabs.scope.service.CarPlateResponse.newBuilder().setCarPlate(SUCCESS_CAR_PLATE).build());
            }

            @Override
            public void onError(Throwable throwable) {
                //N/A
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
