package com.in28minutes.microservice.limitsservice;

import com.in28minutes.microservice.limitsservice.bean.LimitConfiguration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    private final LimitsConfiguration configuration;
    
    public LimitsConfigurationController(LimitsConfiguration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfigurations() {
        return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
    }
}
