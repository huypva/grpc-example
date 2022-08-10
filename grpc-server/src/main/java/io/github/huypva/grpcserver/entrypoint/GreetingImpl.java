package io.github.huypva.grpcserver.entrypoint;

import io.github.huypva.grpc.greeting.GreetingGrpc.GreetingImplBase;
import io.github.huypva.grpc.greeting.GreetingRequest;
import io.github.huypva.grpc.greeting.GreetingResponse;
import io.grpc.stub.StreamObserver;

/**
 * @author huypva
 */
public class GreetingImpl extends GreetingImplBase {

  @Override
  public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {

    String greeting = String.format("Hello %s!", request.getName());
    GreetingResponse response = GreetingResponse.newBuilder()
        .setMessage(greeting)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
