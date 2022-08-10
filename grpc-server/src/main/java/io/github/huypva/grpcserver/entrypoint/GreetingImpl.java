package io.github.huypva.grpcserver.entrypoint;

import io.github.huypva.grpc.greeting.GreetingGrpc.GreetingImplBase;
import io.github.huypva.grpc.greeting.GreetingRequest;
import io.github.huypva.grpc.greeting.GreetingResponse;
import io.grpc.stub.StreamObserver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huypva
 */
@Slf4j
public class GreetingImpl extends GreetingImplBase {

  @SneakyThrows
  @Override
  public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
    Thread.sleep(1000);
    log.info("GreetingImpl {}: request!");
    String greeting = String.format("Hello %s!", request.getName());
    GreetingResponse response = GreetingResponse.newBuilder()
        .setMessage(greeting)
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
