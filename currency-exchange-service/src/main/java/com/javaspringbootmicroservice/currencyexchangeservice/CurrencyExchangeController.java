package com.javaspringbootmicroservice.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment; //Spring boot offers environment class to get the environment properties
    @Autowired
    CurrencyExchangeRepository repository;
    @GetMapping("/currency_exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExhangeValue(
            @PathVariable String from,
            @PathVariable String to
            ){
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if(null == currencyExchange)
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        //Get the port of the CurrencyExchange in use from environment
        String port = environment.getProperty("local.server.port");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
