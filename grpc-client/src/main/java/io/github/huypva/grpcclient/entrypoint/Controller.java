package io.github.huypva.grpcclient.entrypoint;

import io.github.huypva.grpc.greeting.GreetingGrpc;
import io.github.huypva.grpc.greeting.GreetingRequest;
import io.github.huypva.grpc.greeting.GreetingResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huypva
 */
@RestController
public class Controller {

  @GetMapping("/greet")
  public String greet(@RequestParam(name = "name") String name) {
    ManagedChannel channel = ManagedChannelBuilder
        .forAddress("localhost", 8081)
        .usePlaintext().build();

    GreetingGrpc.GreetingBlockingStub stub = GreetingGrpc.newBlockingStub(channel);

    GreetingRequest request = GreetingRequest.newBuilder()
        .setName(name).build();

    GreetingResponse helloResponse = stub.greet(request);
    channel.shutdown();

    return helloResponse.getMessage();
  }
}
