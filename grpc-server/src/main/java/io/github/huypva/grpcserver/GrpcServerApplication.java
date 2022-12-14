package io.github.huypva.grpcserver;

import io.github.huypva.grpcserver.entrypoint.GreetingImpl;
import io.github.huypva.grpcserver.entrypoint.GrpcServerInterceptor;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class GrpcServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrpcServerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void grpcServer() throws IOException, InterruptedException {
		Server server = ServerBuilder
				.forPort(8081)
				.addService(ServerInterceptors.intercept(new GreetingImpl(), new GrpcServerInterceptor("A")))
				.build();

		server.start();
		server.awaitTermination();
	}

}
