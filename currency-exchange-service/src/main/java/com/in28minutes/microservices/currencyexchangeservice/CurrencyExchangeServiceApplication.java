package com.in28minutes.microservices.currencyexchangeservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class CurrencyExchangeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyExchangeServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataLoader(ExchangeValueRepository repository) {
        return args -> {
            ExchangeValue exchangeValue = new ExchangeValue();
            exchangeValue.setFrom("USD");
            exchangeValue.setTo("INR");
            exchangeValue.setConversionMultiple(BigDecimal.valueOf(65));

            ExchangeValue exchangeValue2 = new ExchangeValue();
            exchangeValue2.setFrom("EUR");
            exchangeValue2.setTo("INR");
            exchangeValue2.setConversionMultiple(BigDecimal.valueOf(75));

            ExchangeValue exchangeValue3 = new ExchangeValue();
            exchangeValue3.setFrom("AUD");
            exchangeValue3.setTo("INR");
            exchangeValue3.setConversionMultiple(BigDecimal.valueOf(25));

            repository.saveAll(Arrays.asList(exchangeValue, exchangeValue2, exchangeValue3));
        };
    }

}
