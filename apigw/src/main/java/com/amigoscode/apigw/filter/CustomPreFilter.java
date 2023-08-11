package com.amigoscode.apigw.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * adds a custom header to the incoming request
 */
@Component
@Slf4j
public class CustomPreFilter extends AbstractGatewayFilterFactory<CustomPreFilter.Config> {

  public CustomPreFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      // Perform pre-filtering tasks here
      ServerHttpRequest request = exchange.getRequest();

      if (!request.getHeaders().containsKey("Authorization")) {
        log.info("No Authorization header");
        return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
      }

      String authorizationHeader = request.getHeaders().get("Authorization").get(0);

      if (!this.isAuthorizationValid(authorizationHeader)) {
        log.info("Invalid Authorization header");
        return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
      }

      // Modify the request by adding a custom header
      ServerHttpRequest modifiedRequest = request.mutate().
          header("secret", "secret-value").build();
      log.info("Add header secret={} to Incoming Request {}",
          request.getHeaders().get("secret"), request.getPath());

      // Call the next filter in the chain
      return chain.filter(exchange.mutate().request(modifiedRequest).build());
    };
  }

  private boolean isAuthorizationValid(String authorizationHeader) {
    // Logic for checking the value
    boolean isValid = "aaaa".equals(authorizationHeader);
    return isValid;
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(httpStatus);

    return response.setComplete();
  }

  public static class Config {
    // Put the configuration properties
  }
}