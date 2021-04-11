package com.course.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class LoginAdminGatewayFilter implements GatewayFilter, Ordered {

    private static final Logger LOG = LoggerFactory.getLogger(LoginAdminGatewayFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取header的token参数
        String token = exchange.getRequest().getHeaders().getFirst("token");
        LOG.info("控台登录验证开始，token：{}", token);
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 1;
    }
}