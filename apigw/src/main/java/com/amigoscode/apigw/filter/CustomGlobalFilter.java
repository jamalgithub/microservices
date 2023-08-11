package com.amigoscode.apigw.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Adds a custom header to every incoming request
 */
@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE; // Set the order value as needed
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    // Add a custom header to the request
    ServerHttpRequest request = exchange.getRequest();
    request.mutate()
        .headers(headers -> headers.add("X-Custom-Global-Header", "Global-Filter-Value")).build();

    // Log information about the request
    log.info("Add header X-Custom-Global-Header={} to Incoming Request {}",
        request.getHeaders().get("X-Custom-Global-Header"), request.getPath());

    // Call the next filter in the chain
    return chain.filter(exchange);
  }
}
