package com.in28minutes.microservices.currencyexchangeservice;

public class ExchangeValueNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public ExchangeValueNotFoundException(String message) {
        super(message);
    }
}
