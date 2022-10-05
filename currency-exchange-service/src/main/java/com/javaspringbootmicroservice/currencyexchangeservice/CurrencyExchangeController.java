package com.javaspringbootmicroservice.currencyexchangeservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @GetMapping("/currency_exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExhangeValue(
            @PathVariable String from,
            @PathVariable String to
            ){
        return new CurrencyExchange(100L, from, to, BigDecimal.valueOf(50));
    }
}
