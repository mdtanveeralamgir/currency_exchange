package com.java.springboot.microservices.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f
                                .addRequestHeader("MyHeader", "MyURI")//adding to the header in the request as Key (my header) value (my uri) pair
                                .addRequestParameter("Param", "MyValue"))//passing param in the request as Key (Param) value (MyValue) pair

                        .uri("http://httpbin.org:80")) //If the method is get then redirect to http://httpbin.org:80
                        //Currency exchange
                        .route(p -> p.path("/currency_exchange/**") //If the request comes to this uri with anything after that(up to the name of service in @GetMaping url)
                                        //Then redirect it to naming server and also do load balancing. lb://<name of the service in eureka>
                                        //The shorter url would be: http://localhost:8765/currency_exchange/from/USD/to/INR
                                        .uri("lb://currency-exchange"))
                        //Currency conversion rest template
                        .route(p -> p.path("/currency_conversion/**").uri("lb://currency-conversion"))
                        //Currency conversion rest feign
                        .route(p -> p.path("/currency_conversion_feign/**").uri("lb://currency-conversion"))
                        //define a new url (without defining in the controller of any service) and redirecting it to a service
                        .route(p -> p.path("/currency_conversion_new/**")
                                .filters(f -> f.rewritePath("/currency_conversion_new/(?<segment>.*)","/currency_conversion_feign/${segment}"))//replace /currency_conversion_new/ with /currency_conversion_feign/ and use a regular expression to identify the remaining items in the url as a segment
                                .uri("lb://currency-conversion"))
                .build();
    }
}
