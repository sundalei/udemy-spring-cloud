package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class  CurrencyConversionController {
	
	private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

        private final CurrencyExchangeServiceProxy currencyExchangeService;
        private final RestTemplate restTemplate;

        @Value("${currency_exchange_url}")
        private String currencyExchangeUrl;

        public CurrencyConversionController(CurrencyExchangeServiceProxy currencyExchangeService,
                                            RestTemplate restTemplate) {
                this.currencyExchangeService = currencyExchangeService;
                this.restTemplate = restTemplate;
        }

        @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversionBean convertCurrency(@PathVariable String from,
                        @PathVariable String to,
                        @PathVariable BigDecimal quantity) {
        	
        	//CHANGE-KUBERNETES
        	logger.info("calculateCurrencyConversion called with {} to {} with {}", from, to, quantity);
                logger.info("currency exchange url {}", currencyExchangeUrl);

                Map<String, String> uriVariables = new HashMap<>();
                uriVariables.put("from", from);
                uriVariables.put("to", to);

                ResponseEntity<CurrencyConversionBean> responseEntity = restTemplate.getForEntity(
                                "http://" + currencyExchangeUrl + ":8000/currency-exchange/from/{from}/to/{to}",
                                CurrencyConversionBean.class, uriVariables);

                CurrencyConversionBean body = responseEntity.getBody();
                assert body != null;
                return new CurrencyConversionBean(body.getId(), from, to, body.getConversionMultiple(), quantity,
                                quantity.multiply(body.getConversionMultiple()), body.getEnvironment() + " from resttemplate");
        }

        @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
        public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,
                        @PathVariable String to,
                        @PathVariable BigDecimal quantity) {

                CurrencyConversionBean body = currencyExchangeService.retrieveExchangeValue(from, to);
                return new CurrencyConversionBean(body.getId(), from, to, body.getConversionMultiple(), quantity,
                                quantity.multiply(body.getConversionMultiple()), body.getEnvironment() + " from feign");
        }

}
