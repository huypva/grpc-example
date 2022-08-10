package io.github.huypva.grpcserver.entrypoint;

import io.grpc.ForwardingServerCall;
import io.grpc.ForwardingServerCallListener;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCall.Listener;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.Status;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huypva
 */
@Slf4j
@AllArgsConstructor
public class GrpcServerInterceptor implements ServerInterceptor {

  String name;

  @SneakyThrows
  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall,
      Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
//    return serverCallHandler.startCall(serverCall, metadata);
    Thread.sleep(1000);
    log.info("GrpcServerInterceptor {}: interceptCall!", name);

    ServerCall<ReqT, RespT> wrapperCall =
        new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(serverCall) {

          @SneakyThrows
          @Override
          public void request(int numMessages) {
            Thread.sleep(1000);
            log.info("ForwardingServerCall {}: request!", name);
            super.request(numMessages);
          }

          @SneakyThrows
          @Override
          public void sendHeaders(Metadata headers) {
            Thread.sleep(1000);
            log.info("ForwardingServerCall {}: sendHeaders!", name);
            super.sendHeaders(headers);
          }

          @SneakyThrows
          @Override
          public void sendMessage(RespT message) {
            Thread.sleep(1000);
            log.info("ForwardingServerCall {}: sendMessage!", name);
            super.sendMessage(message);
          }

          @SneakyThrows
          @Override
          public void close(Status status, Metadata metadata) {
            Thread.sleep(1000);
            log.info("ForwardingServerCall {}: close!", name);
            super.close(status, metadata);
          }
        };

//    return serverCallHandler.startCall(wrapperCall, metadata);
    ServerCall.Listener<ReqT> listener = serverCallHandler.startCall(wrapperCall, metadata);
    return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(listener) {

      @SneakyThrows
      @Override
      public void onMessage(ReqT message) {
        Thread.sleep(1000);
        log.info("ForwardingServerCallListener {}: onMessage!", name);
        super.onMessage(message);
      }

      @SneakyThrows
      @Override
      public void onHalfClose() {
        Thread.sleep(1000);
        log.info("ForwardingServerCallListener {}: onHalfClose!", name);
        super.onHalfClose();
      }

      @SneakyThrows
      @Override
      public void onCancel() {
        Thread.sleep(1000);
        log.info("ForwardingServerCallListener {}: onCancel!", name);
        super.onCancel();
      }

      @SneakyThrows
      @Override
      public void onComplete() {
        Thread.sleep(1000);
        log.info("ForwardingServerCallListener {}: onComplete!", name);
        super.onComplete();
      }

      @SneakyThrows
      @Override
      public void onReady() {
        Thread.sleep(1000);
        log.info("ForwardingServerCallListener {}: onReady!", name);
        super.onReady();
      }
    };

  }
}
