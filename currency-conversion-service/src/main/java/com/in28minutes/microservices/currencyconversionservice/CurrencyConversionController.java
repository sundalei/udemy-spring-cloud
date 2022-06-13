package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class  CurrencyConversionController {

        private final CurrencyExchangeServiceProxy currencyExchangeService;

        @Autowired
        public CurrencyConversionController(CurrencyExchangeServiceProxy currencyExchangeService) {
                this.currencyExchangeService = currencyExchangeService;
        }

        @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversionBean convertCurrency(@PathVariable String from,
                        @PathVariable String to,
                        @PathVariable BigDecimal quantity) {

                Map<String, String> uriVariables = new HashMap<>();
                uriVariables.put("from", from);
                uriVariables.put("to", to);

                ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                                CurrencyConversionBean.class, uriVariables);

                CurrencyConversionBean body = responseEntity.getBody();
                return new CurrencyConversionBean(body.getId(), from, to, body.getConversionMultiple(), quantity,
                                quantity.multiply(body.getConversionMultiple()), body.getEnvironment());
        }

        @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
                        @PathVariable String to,
                        @PathVariable BigDecimal quantity) {

                CurrencyConversionBean body = currencyExchangeService.retrieveExchangeValue(from, to);
                return new CurrencyConversionBean(body.getId(), from, to, body.getConversionMultiple(), quantity,
                                quantity.multiply(body.getConversionMultiple()), body.getEnvironment());
        }

}
