package com.pintec.springcloud.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author yang.zhou
 * @date 2019.08.20.14.
 */
@Component
@Slf4j
public class RequestBodyFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String requestBody = resolveBodyFromRequest(exchange.getRequest());

        Object requestBody = exchange.getAttribute("cachedRequestBodyObject");
        log.info("request body is:{}", requestBody);

//        return chain.filter(exchange);
        return chain.filter(exchange).then(Mono.fromRunnable(() -> System.out.println("RequestFilter post filter")));
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    /**
     * 这种方法在spring-boot-starter-parent 2.0.6.RELEASE + Spring Cloud Finchley.SR2 body 中生效，
     * 但是在spring-boot-starter-parent 2.1.0.RELEASE + Spring Cloud Greenwich.M3 body 中不生效，总是为空
     *
     * @param serverHttpRequest
     * @return
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        Flux<DataBuffer> body = serverHttpRequest.getBody();
        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        return bodyRef.get();
    }
}
