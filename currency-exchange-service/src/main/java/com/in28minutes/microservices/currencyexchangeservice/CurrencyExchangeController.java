package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final Environment environment;

	private final ExchangeValueRepository repository;

	@Autowired
	public CurrencyExchangeController(Environment environment, ExchangeValueRepository repository) {
		this.environment = environment;
		this.repository = repository;
	}

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {

		Optional<ExchangeValue> optional = repository.findByFromAndTo(from, to);
		if (optional.isEmpty()) {
			throw new ExchangeValueNotFoundException(String.format("Exchange from %s to %s is not found.", from, to));
		}

		ExchangeValue exchangeValue = optional.get();
		String port = environment.getProperty("local.server.port");
		log.info(String.format("port %s", port));
		exchangeValue.setPort(Integer.parseInt(port));
		return exchangeValue;
	}

}
