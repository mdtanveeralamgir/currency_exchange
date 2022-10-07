package com.java.springboot.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//The name defined in application.properties in CurrencyExchange
//This one is without load balancing
//@FeignClient(name="currency-exchange", url="localhost:8001")

//By just removing the URL the load balancing will take place and eureka and feign will do the job
@FeignClient(name="currency-exchange")

public interface CurrencyExchangeProxy {

    //Matching the uri of CurrencyExchange
    //Since the properties name are same in CurrencyExchange and CurrencyConverter so they will map
    @GetMapping("/currency_exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExhangeValue(
            @PathVariable String from,
            @PathVariable String to);
}
