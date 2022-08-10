package io.github.huypva.grpcclient.entrypoint;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huypva
 */
@Slf4j
@AllArgsConstructor
public class GrpcClientInterceptor implements ClientInterceptor {

  public String name;

  @SneakyThrows
  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> methodDescriptor,
      CallOptions callOptions, Channel channel) {
    Thread.sleep(1000);
    log.info("GrpcClientInterceptor {}: interceptCall!", name);
    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        channel.newCall(methodDescriptor, callOptions)) {

      @SneakyThrows
      @Override
      public void start(Listener<RespT> listener, Metadata headers) {
        log.info("ForwardingClientCall {}: start!", name);
        Thread.sleep(1000);
        ClientCall.Listener<RespT> clientCallListener =
            new ForwardingClientCallListener<RespT>() {
              @SneakyThrows
              @Override
              protected Listener<RespT> delegate() {
                return listener; // from ForwardingClientCall
              }

              @SneakyThrows
              @Override
              public void onReady() {
                Thread.sleep(1000);
                log.info("ClientCall.Listener {}: onReady!", name);
                super.onReady();
              }

              @SneakyThrows
              @Override
              public void onHeaders(Metadata headers) {
                Thread.sleep(1000);
                log.info("ClientCall.Listener {}: onHeaders!", name);
                super.onHeaders(headers);
              }

              @SneakyThrows
              @Override
              public void onMessage(RespT message) {
                Thread.sleep(1000);
                log.info("ClientCall.Listener {}: onMessage!", name);
                super.onMessage(message);
              }

              @SneakyThrows
              @Override
              public void onClose(Status status, Metadata trailers) {
                Thread.sleep(1000);
                log.info("ClientCall.Listener {}: onClose!", name);
                super.onClose(status, trailers);
              }
            };
        super.start(clientCallListener, headers);
      }

      @SneakyThrows
      @Override
      public void sendMessage(ReqT message) {
        Thread.sleep(1000);
        log.info("ForwardingClientCall {}: sendMessage!", name);
        super.sendMessage(message);
      }

      @SneakyThrows
      @Override
      public void request(int numMessages) {
        Thread.sleep(1000);
        log.info("ForwardingClientCall {}: request!", name);
        super.request(numMessages);
      }

      @SneakyThrows
      @Override
      public void halfClose() {
        Thread.sleep(1000);
        log.info("ForwardingClientCall {}: halfClose!", name);
        super.halfClose();
      }

      @SneakyThrows
      @Override
      public void cancel(String message, Throwable cause) {
        Thread.sleep(1000);
        log.info("ForwardingClientCall {}: cancel!", name);
        super.cancel(message, cause);
      }
    };
  }

}
