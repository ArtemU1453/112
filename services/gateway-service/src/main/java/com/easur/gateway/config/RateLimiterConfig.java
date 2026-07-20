package com.easur.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;

import java.net.InetSocketAddress;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> resolveKey(exchange);
    }

    private Mono<String> resolveKey(ServerWebExchange exchange) {
        return exchange.getPrincipal()
                .map(principal -> {
                    if (principal instanceof Authentication) {
                        return ((Authentication) principal).getName();
                    }
                    return principal.getName();
                })
                .switchIfEmpty(Mono.fromSupplier(() -> {
                    InetSocketAddress addr = exchange.getRequest().getRemoteAddress();
                    return addr != null ? addr.getAddress().getHostAddress() : "unknown";
                }));
    }
}
