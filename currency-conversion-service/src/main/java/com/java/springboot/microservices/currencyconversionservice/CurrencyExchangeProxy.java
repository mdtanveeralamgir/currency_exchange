package com.java.springboot.microservices.currencyconversionservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="currency-exchange", url="localhost:8000") //The name defined in application.properties in CurrencyExchange
public interface CurrencyExchangeProxy {

    //Matching the uri of CurrencyExchange
    //Since the properties name are same in CurrencyExchange and CurrencyConverter so they will map
    @GetMapping("/currency_exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExhangeValue(
            @PathVariable String from,
            @PathVariable String to);
}
