package com.amigoscode.apigw.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * logs the response status code after the response has been processed
 */
@Component
@Slf4j
public class CustomPostFilter extends AbstractGatewayFilterFactory<CustomPostFilter.Config> {

  public CustomPostFilter() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      // Call the next filter in the chain and capture the response
      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
        // Get the response status code and log it
        ServerHttpResponse response = exchange.getResponse();
        int statusCode = response.getStatusCode().value();
        log.info("Response Status Code: {}", statusCode);
      }));
    };
  }

  public static class Config {
    // You can define configuration properties for your filter here if needed
  }
}