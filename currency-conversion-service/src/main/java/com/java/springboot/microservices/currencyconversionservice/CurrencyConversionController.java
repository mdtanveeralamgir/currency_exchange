package com.java.springboot.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    //Using traditional way
    @GetMapping("currency_conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion retrieveConvertedValue(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
    {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        //Calling a rest api
        //from and to will be passed by hashmap (uriVariables)
        //the properties name of CurrencyExchange bean should be same as CurrencyConversion properties in order to map properly
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8001/currency_exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);
        CurrencyConversion currencyConversion = responseEntity.getBody();
        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());
    }

    //Using feign proxy. Recommended way
    @Autowired
    private CurrencyExchangeProxy proxy;
    @GetMapping("currency_conversion_feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion retrieveConvertedFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
    {

        //Using feign proxy to get the value from CurrencyExchange API
        CurrencyConversion currencyConversion = proxy.retrieveExhangeValue(from, to);
        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity, currencyConversion.getConversionMultiple(), quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment());
    }
}
