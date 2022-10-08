package com.javaspringbootmicroservice.currencyexchangeservice;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {
    private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
    @GetMapping("/sample-api")
//    @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")//retry upon failure using default configuration
//    @CircuitBreaker(name = "sample-api", fallbackMethod = "hardCodedResponse")
    @RateLimiter(name = "default") //certain number of requests are allowed in certain period of time
    @Bulkhead(name = "default")
    public String sampleApi()
    {
        logger.info("Sample Api call received");
//        ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/intend-to-fail", String.class);
//        return forEntity.getBody();
        return "sample-api";
    }

    //After the retry shows this response in the page
    public String hardCodedResponse(Exception ex)
    {
        return "fallback-response";
    }
}
