package com.in28minutes.microservices.currencyexchangeservice;

public class ExchangeValueNotFoundException extends RuntimeException {

    public ExchangeValueNotFoundException(String message) {
        super(message);
    }
}
