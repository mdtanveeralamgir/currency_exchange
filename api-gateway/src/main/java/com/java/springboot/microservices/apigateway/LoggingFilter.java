package com.java.springboot.microservices.apigateway;
//This class will act as a global logging system
//it will log in the console (run console)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
@Component
public class LoggingFilter implements GlobalFilter {
    private Logger logger = LoggerFactory.getLogger((LoggingFilter.class));
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //Logging the url in the run console
        logger.info("path of the request received -> {}", exchange.getRequest().getPath()); //Logging the information about the request which is present in the exchange param
        return chain.filter(exchange); //Letting the execution continue as it was
    }
}
